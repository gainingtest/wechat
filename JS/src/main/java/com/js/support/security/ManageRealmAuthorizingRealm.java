package com.js.support.security;


import java.util.Collection;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.js.modules.user.dao.mybatis.entity.user;
import com.js.modules.user.service.UserService;
import com.js.support.dto.UserInfoDTO;
import com.js.support.util.EncoderUtil;
import com.js.support.util.PatterValidate;
public class ManageRealmAuthorizingRealm extends AuthorizingRealm {

	private final static Logger logger = LoggerFactory.getLogger(ManageRealmAuthorizingRealm.class);

	@Autowired
	private UserService userService;
//	@Autowired
//	private UserAuthorityService userAuthorityService;
//	@Autowired
//	private UserOutService userOutService;
//	@Autowired
//	private UserBaseService userBaseService;
//	@Autowired
//	private UserInfoHandleService userInfoHandleService;
//	@Autowired
//	private UserWxBindHandleService userWxBindHandleService;
	

	/**
	 * 设定Password校验.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		// 该句作用是重写shiro的密码验证，让shiro用我自己的验证
		setCredentialsMatcher(new CustomCredentialsMatcher());
	}

	/**
	 * 权限判断
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		UserInfoDTO userInfoDTO = (UserInfoDTO) SecurityUtils.getSubject().getPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		// 获取用户等级
		if (userInfoDTO == null || userInfoDTO.getUserLevel() == null || StringUtils.isNotBlank(userInfoDTO.getUserLevel().toString())) {
			return authorizationInfo;
		}
		// TODO
		Set<String> set = null;
		try {
//			set = userAuthorityService.getAuthorityByLevel(userInfoDTO.getUserLevel());
		} catch (Exception ex) {
			logger.error("用户权限查询异常", ex);
		}
		authorizationInfo.setStringPermissions(set);
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		CustomUsernamePasswordToken customToken = (CustomUsernamePasswordToken) token;
		// 转化用户信息放入session，用于用户登录成功后使用
		String openid = customToken.getOpenid();
		if (!customToken.getLoginType().equals("weChatTypeByMobile")
				&& !"weChatTypeByOpenid".equals(customToken.getLoginType())) {
			logger.error("非法登录，登录方式正确");
			throw new IncorrectLoginParameterException("请选择正确的登录方式登录");
		}
		if (StringUtils.isBlank(openid)) {
			logger.error("登录失败，未获取到微信用户openid信息");
			throw new IncorrectLoginParameterException("未获取到您的绑定信息");
		}
		if (StringUtils.isBlank(customToken.getUsername()) && customToken.getLoginType().equals("weChatTypeByMobile")) {
			logger.error("登录失败，微信用户手机号登录方式时：手机号为空");
			throw new IncorrectLoginParameterException("您输入的手机号不正确");
		}
		if (!StringUtils.isBlank(customToken.getUsername())
				&& customToken.getLoginType().equals("weChatTypeByMobile")) {
			if (!PatterValidate.isPatterEnum(customToken.getUsername(), PatterValidate.PHONE)) {
				logger.error("登录失败，微信用户手机号登录方式时：手机号【{}】格式不正确", customToken.getUsername());
				throw new IncorrectLoginParameterException("您输入的手机号不正确");
			}
			if (customToken.getPassword() == null || new String(customToken.getPassword()).length() < 6) {
				logger.error("登录失败，密码长度不正确");
				throw new IncorrectLoginParameterException("您输入的密码不正确");
			}
		}
		UserInfoDTO userInfoDTO = authenticationUserInfo(customToken);
		userInfoDTO.setOpenid(customToken.getOpenid());
		// 更新session
		SecurityUtils.getSubject().getSession().setAttribute(userInfoDTO.getMobile(), userInfoDTO);
		logger.info("打印出sessionId" + SecurityUtils.getSubject().getSession().getId());
		// //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userInfoDTO, // 用户名
				"", // 密码
				ByteSource.Util.bytes(userInfoDTO.getUserId() + userInfoDTO.getMobile() + "js"),
				userInfoDTO.getMobile());
		return authenticationInfo;
	}

	private UserInfoDTO authenticationUserInfo(CustomUsernamePasswordToken customToken) {
		user userInfo = null;
		boolean bindWx = false;
		// 根据openid查询用户信息
		if ("weChatTypeByOpenid".equals(customToken.getLoginType())) {
			userInfo = userService.findUserInfoByOpenid(customToken.getOpenid());
			if (userInfo == null ) {
				logger.info("微信号【{}】未绑定账号", customToken.getOpenid());
				throw new UnknownAccountException("该微信号未绑定账号");
			}
		
		}
		if ("weChatTypeByMobile".equals(customToken.getLoginType())) {
			userInfo = userService.findByNumber(customToken.getUsername());
			if (userInfo == null ) {
				logger.info("手机号【{}】未注册", customToken.getOpenid());
				throw new UnknownAccountException("您的账号尚未注册，请先注册!");
			}
			
			
		}
		
		// 用手机号登录的判断密码是否正确
		if ("weChatTypeByMobile".equals(customToken.getLoginType()) && !userInfo.getPassword()
				.equals(EncoderUtil.encodeByMD5Substring(new String(customToken.getPassword())))) {
			logger.info("用户密码不正确，输入密码【{}】,对比密码【{}】",
					EncoderUtil.encodeByMD5Substring(new String(customToken.getPassword())), userInfo.getPassword());
			throw new CredentialsException("您输入的密码有误，请输入正确的登录密码");
		}
	
		// 查询资料完善度
		String userDegree = "";
		// 查询用户认证状态
		String authenState = "";
		
		// 查询微信用户头像
		String imgUrl = "";
		try {
			//TODO
//			imgUrl = userOutService.getWeiXinImgUrl(customToken.getOpenid());
		} catch (Exception e) {
			logger.error("获取微信用户信息，用户openId【{}】。错误记录【{}】", customToken.getOpenid(), e.getMessage());
		}
//		// 用户状态设置为正常
//		if(!UserRelatedValues.normal.equals(userInfo.getUserState())){
//			userInfo.setUserState(UserRelatedValues.normal);
//			userBaseService.updateUserState(userInfo);
//		}
		UserInfoDTO userInfoDTO = new UserInfoDTO();
//		userInfoDTO.setAddress(userInfo.getAddress());
		userInfoDTO.setUserId(userInfo.getId()+"");
//		userInfoDTO.setBirthday(userInfo.getBirthday());
//		userInfoDTO.setArea(userInfo.getArea());
//		userInfoDTO.setCity(userInfo.getCity());
//		userInfoDTO.setEmail(userInfo.getEmail());
//		userInfoDTO.setGender(userInfo.getGender());
//		userInfoDTO.setHomePhone(userInfo.getHomePhone());
		userInfoDTO.setIdNo(userInfo.getIdNo());
//		userInfoDTO.setIdType(userInfo.getIdType());
//		userInfoDTO.setIsValidation(userInfo.getIsValidation());
//		userInfoDTO.setLastLoginTime(userInfo.getLastLoginTime());
//		userInfoDTO.setMarritalStatus(userInfo.getMarritalStatus());
		userInfoDTO.setMobile(userInfo.getMobile());
//		userInfoDTO.setNickName(userInfo.getNickName());
//		userInfoDTO.setOccupation(userInfo.getOccupation());
//		userInfoDTO.setProvince(userInfo.getProvince());
//		userInfoDTO.setUserState(userInfo.getUserState());
//		userInfoDTO.setUserName(userInfo.getUserName());
//		userInfoDTO.setUserSource(userInfo.getUserSource());
//		userInfoDTO.setValidationDate(userInfo.getValidationDate());
//		userInfoDTO.setWorkPhone(userInfo.getWorkPhone());
		userInfoDTO.setAuthenState(authenState);
		userInfoDTO.setUserDegree(userDegree);
		userInfoDTO.setImgUrl(imgUrl);
//		userInfoDTO.setUserLevel(userInfo.getUserLevel());
		return userInfoDTO;
	}

	@Override
	public boolean isPermitted(PrincipalCollection principals, String permission) {
		if(StringUtils.isBlank(permission) || principals == null){
			logger.info("传入参数为空：permission：【{}】",permission);
			return false;
		}
		AuthorizationInfo info = getAuthorizationInfo(principals);
		Collection<String> permissions = info.getStringPermissions();
		return permissions.contains(permission) || patternMatch(permissions, permission);
	}

	public boolean patternMatch(Collection<String> patternUrlList, String url) {
		if (patternUrlList == null || patternUrlList.size() == 0) {
			return false;
		}
		boolean flag = false;
		for (String patternUrl : patternUrlList) {
			if (StringUtils.isNotEmpty(patternUrl)) {
				Pattern pattern = Pattern.compile(patternUrl);
				Matcher matcher = pattern.matcher(url);
				if (matcher.matches()) {
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

}