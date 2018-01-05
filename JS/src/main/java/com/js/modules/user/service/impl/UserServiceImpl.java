package com.js.modules.user.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.js.modules.sms.service.SmsService;
import com.js.modules.user.dao.mybatis.entity.user;
import com.js.modules.user.dao.mybatis.entity.userExample;
import com.js.modules.user.dao.mybatis.entity.userExample.Criteria;
import com.js.modules.user.dao.mybatis.mapper.userMapper;
import com.js.modules.user.service.UserService;
import com.js.support.exception.CommonException;
import com.js.support.message.Message;
import com.js.support.util.AssistParameter;
import com.js.support.util.BaseRule;
import com.js.support.util.DateUtil;
@Service
public class UserServiceImpl implements UserService {

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private SmsService smsVerifySerivce;
	@Autowired
	private userMapper usermapper;
	/**
	 * 微信用户注册
	 * @throws Exception 
	 */
	@Override
	public String weChatRegister(user u, String openid, AssistParameter assistParameter) throws Exception {
		//验证
		if (StringUtils.isBlank(assistParameter.getSmsCode())) {
			logger.info("官微注册失败：验证码为空smsCode【{}】", assistParameter.getSmsCode());
			throw new CommonException("请输入短信验证码");
		}
//		 图片验证码校验
		BaseRule.checkEquals(assistParameter.getSessionCode(), assistParameter.getCode());
//		 手机校验
		if (!smsVerifySerivce.smsVertify(u.getMobile(), assistParameter.getSmsCode(), "login").getResult()) {
			logger.info("您的短信验证码错误请重新输入,输入【{}】", assistParameter.getSmsCode());
			throw new CommonException("您的短信验证码错误请重新输入");
		}
		//判断手机号是否已经被注册
		if(!isExitMobile(u.getMobile())){
			throw new CommonException("该手机号已经被注册");
		}
		//判断openid是否已经被绑定
		Message message=isExitOpenid(openid);
		if(!message.getResult()){
			throw new CommonException("微信号已经被"+message.getInfo()+"绑定");
		}
		//进行注册
		u.setOpenid(openid);
		
		if(insertUser(u)){
			return "注册成功";
		}
		return "";
	}
	private boolean isExitMobile(String mobile) {
		userExample example=new userExample();
		Criteria criteria=example.createCriteria();
		criteria.andMobileEqualTo(mobile);
		List<user> userlist=usermapper.selectByExample(example);
		if(userlist!=null&&userlist.size()>0){
			return false;
		}
		return true;
	}
	private boolean insertUser(user u) {
		u.setCreateDate(DateUtil.currentDate());
		u.setCreateTime(DateUtil.currentDate(DateUtil.HHMMSS));
		if(usermapper.insert(u)>0){
			return true;
		}
		return false;
	}
	private Message isExitOpenid(String openid) {
		userExample example=new userExample();
		Criteria criteria=example.createCriteria();
		criteria.andOpenidEqualTo(openid);
		List<user> userlist=usermapper.selectByExample(example);
		if(userlist!=null&&userlist.size()>0){
			return new Message(false,userlist.get(0).getMobile());
		}
		return new Message(true,"空");
	}

	@Override
	public user findByNumber(String mobile){
		userExample example=new userExample();
		Criteria criteria=example.createCriteria();
		criteria.andMobileEqualTo(mobile);
		List<user> userlist=usermapper.selectByExample(example);
		if(userlist!=null&&userlist.size()>0){
			return userlist.get(0);
		}
		return null;
	}
	

	@Override
	public user findUserInfoByOpenid(String openid){
		userExample example=new userExample();
		Criteria criteria=example.createCriteria();
		criteria.andOpenidEqualTo(openid);
		List<user> userlist=usermapper.selectByExample(example);
		if(userlist!=null&&userlist.size()>0){
			return userlist.get(0);
		}
		return null;
	}
	
}
