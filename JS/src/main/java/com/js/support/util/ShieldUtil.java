package com.js.support.util;


import org.apache.commons.lang3.StringUtils;


/**
 * 屏蔽字符串工具类
 * @author 
 *
 */
public class ShieldUtil {

	/**
	 * 屏蔽证件号码
	 * 身份证号部分屏蔽
	 * 18位身份证件和户口本显示前12位，后6位屏蔽，如420601196910******；
	 * 15位身份证件和户口本显示前9位，后6位屏蔽
	 * 
	 * 其他证件号码屏蔽
	 * 其它证件类型最后三位屏蔽，如LA988***；
	 * @param idType 证件类型
	 * @param idNo   证件号码
	 * @return
	 */
	public static String shieldIdNumber(String idType, String idNo){
		if(StringUtils.isNotBlank(idType) && StringUtils.isNotBlank(idNo)){
			if(idType.equals(CodeTable.CARD_IDENTITY) || idType.equals(CodeTable.CARD_ACCOUNT)){
				return idNo.substring(0, idNo.length()-6)+"******";
			} else {
				return idNo.substring(0, idNo.length()-3)+"***";
			}
		}
		return "";
	}
	
	/**
	 * 手机号码屏蔽
	 * 手机号显示前三、后四位，中间四位用*代替
	 * @param phoneNo
	 * @return
	 */
	public static String shieldPhoneNo(String phoneNo){
		if(StringUtils.isNotBlank(phoneNo) && !phoneNo.contains("***") && phoneNo.length() > 7){
			return phoneNo.substring(0, 3) + "****" + phoneNo.substring(7, 11);
		}
		return phoneNo;
	}
	
	/**
	 * 银行账号屏蔽
	 * 银行账号只显示后四位
	 * @param accNo
	 * @return
	 */
	public static String shieldAccNo(String accNo){
		if(StringUtils.isBlank(accNo)){
			return "";
		}
		String replace = accNo.replace(" ", "");
		if(StringUtils.isNotBlank(replace) && replace.length() > 11){
			return "************"+replace.substring(replace.length()-4, replace.length());
		}
		return accNo;
	}

}