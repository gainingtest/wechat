package com.js.support.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


public enum PatterValidate {
	// 邮箱
	EMAIL("\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$"),
	//手机号
	PHONE("^[1][0-9]{10}$"),
	//不包括零的正整数
	POSITIVEINTEGER("^\\+?[1-9][0-9]*$"),
	//身份证验证，只验证位数
	IDNO("IDNO"),
	//日期
	DATE("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?{1}"),
	//邮编
	ZIP("^[0-9]{6}$"),
	//性别
	SEX("^['男'|'女']$");
	private final String pattern;

	private PatterValidate(String pattern) {
		this.pattern = pattern;
	}
	
	public static boolean isPatterEnum(String paramValue, PatterValidate patterEnum) {
		if("IDNO".equals(patterEnum.pattern)){
			return IDCardUtil.validateCard(paramValue);
		}
		Pattern p = Pattern.compile(patterEnum.pattern);
		Matcher m = p.matcher(paramValue);
		return m.matches() || BaseRule.checkHKNumber(paramValue);
		
	}

	public static boolean isPatterEnum(String paramName, String paramValue, PatterValidate patterEnum) {
		for(PatterValidate a : PatterValidate.values()){
			if(paramName.toUpperCase().indexOf(a.toString()) >= 0){
				if("IDNO".equals(patterEnum.pattern)){
					return IDCardUtil.validateCard(paramValue);
				}
				Pattern p = Pattern.compile(patterEnum.pattern);
				Matcher m = p.matcher(paramValue);
				return m.matches();
			}
		}
		return true;
	}

}
