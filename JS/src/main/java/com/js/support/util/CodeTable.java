package com.js.support.util;



public class CodeTable {
	
	public final static String DEFAULT_CHARSET = "UTF-8";
	public final static String CHARSET_UTF8 = "application/json; charset=utf-8";
	
	/** 登录跳转类型 **/
	public final static String REDIRECT_TYPE = "redirect_type";


	/* 服务平台，证件类型 */
	public final static String CARD_IDENTITY = "01"; // 居民身份证
	public final static String CARD_ACCOUNT = "02"; // 居民户口簿
	public final static String CARD_DRIVER = "03"; // 驾驶证
	public final static String CARD_OFFICER = "04"; // 军官证
	public final static String CARD_SOLDIERS = "05"; // 士兵证
	public final static String CARD_OFFICER_RETIRE = "06"; // 军官离退休证
	public final static String CARD_PASSPORT = "07"; // 护照
	public final static String CARD_UNUSUAL = "08"; // 异常身份证
	public final static String CARD_HK_MACAU = "09"; // 港澳通行证
	public final static String CARD_TAIWAN = "10"; // 台湾通行证
	public final static String CARD_HK_MACAU_ID = "11"; // 港澳居民通行证
	public final static String CARD_TAIWAN_ID = "12"; // 台胞证
	public final static String CARD_BIRTH = "13"; // 出生证
	public final static String CARD_OTHER_PASSPORT = "51"; // 外国护照
	public final static String CARD_TRAVEL = "52"; // 旅行证
	public final static String CARD_RESIDE = "53"; // 居留证件
	public final static String CARD_OTHER = "98"; // 其他证件
	public final static String CARD_ARMED_POLICE = "98"; // 武警身份证


	/* 数据来源 */
	public static final String SOURCE_WECHAT = "wechat";

	/* 码表类型 */
	public static final String TYPE_ID = "ID_Type"; // 证件类型
	
	public final static String IS_NULL1 = "--";
	public final static String IS_NULL2 = "";
	/**
	 * 微信默认头像
	 */
	public static final String WX_IMG = "WX_IMG";

	/**
	 * 登录回调地址
	 */
	public static final String QUERY_OPENID = "QUERY_OPENID";
	public static final String JS_REDIRECT_PATH = "JS_REDIRECT_PATH";
	
}