package com.js.support.util;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.js.support.exception.CommonException;


/**
 * 规则校验基类
 * 
 * @author 
 *
 */
public class BaseRule {

	/** 性别女标示 */
	public static final String SEX_FEMALE = "1";
	/** 性别男标示 */
	public static final String SEX_MALE = "0";

	/**
	 * 校验姓名
	 * 
	 * @param name
	 * @return
	 */
	public static boolean cheakUserName(String name) {
		if (StringUtils.isBlank(name)) {
			return false;
		} else {
			name = name.trim();// 清除前后空格之后,进行校验
			String regex1 = "^[\\u4e00-\\u9fa5 ]+$";// 如果全为汉字
			Pattern pattern1 = Pattern.compile(regex1);
			Matcher matcher1 = pattern1.matcher(name);

			String regex2 = "^[a-zA-Z\\u4e00-\\u9fa5/ ]+$";// 如果为不为纯汉字,中英文混合
			Pattern pattern2 = Pattern.compile(regex2);
			Matcher matcher2 = pattern2.matcher(name);
			if (name.length() == 1) {
				return false;
			} else if (matcher1.matches()) {
				// 全部为汉字的情况下,自动清除汉字之间空格之后进行校验
				name = name.replaceAll(" ", "");
				// 全为汉字中间可有"·"
				regex1 = "^[\\u4e00-\\u9fa5]{1}[\\u4e00-\\u9fa5\\·]{0,100}[\\u4e00-\\u9fa5]{1}$";
				pattern1 = Pattern.compile(regex1);
				matcher1 = pattern1.matcher(name);
				if (!matcher1.matches()) {
					return false;
				} else if (name.length() > 42) {
					return false;
				}
			} else if (matcher2.matches()) {
				String result = "";
				String[] tt = name.split("\\s{1,}"); // 按照空格分隔字符串，多个空格按照一个空格进行分隔
				for (int i = 0; i < tt.length; i++) {
					result = result + tt[i] + " ";
				}
				result = result.trim();// 去掉两边空格
				if (result.length() > 42) {
					return false;
				}
				name = result;
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * 描述：检查性别，只能为1男性、2女性
	 * 
	 * @param sex
	 *            性别编号
	 * @return
	 */
	public static boolean checkSex(String sex) {
		if (StringUtils.isBlank(sex)) {
			return false;
		} else {
			sex = sex.trim();
			if (SEX_MALE.equals(sex) || SEX_FEMALE.equals(sex)) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 描述：检查日期是否合法 (yyyy-MM-dd)
	 * 
	 * @param dataString
	 *            日期
	 * @return
	 */
	public static boolean checkDate(String dataString) {
		if (!isDate(dataString, DateUtil.YYYY_MM_DD)) {
			return false;
		}
		return true;
	}

	/**
	 * 检查日期是否合法
	 * 
	 * @param dateString
	 *            日期
	 * @param format
	 *            日期格式，例如yyyy-MM-dd
	 * @return
	 */
	public static boolean checkDate(String dateString, String format) {
		if (!isDate(dateString, format)) {
			return false;
		}
		return true;
	}

	/**
	 * 检查日期是否合法
	 * 
	 * @param dateString
	 *            日期
	 * @param format
	 *            日期格式，例如yyyy-MM-dd
	 * @return
	 */
	public static boolean isDate(String dateString, String format) {
		if (StringUtils.isBlank(dateString) || StringUtils.isBlank(format)) {
			return false;
		}
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			Date birthDate = dateFormat.parse(dateString);
			String birthDateStr = dateFormat.format(birthDate);
			if (birthDateStr.equals(dateString)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 检查证件号是否合法
	 * 
	 * @param idType
	 *            证件类型
	 * @param idNumber
	 *            证件号码
	 * @return
	 */
	public static boolean checkCard(String idType, String idNumber) {
		if (StringUtils.isBlank(idType)) {
			return false;
		} else if (StringUtils.isBlank(idNumber)) {
			return false;
		}
		if (idType.equals(CodeTable.CARD_IDENTITY)) {
			return IDCardUtil.verify(idNumber); /* 校验身份证、户口本号码 */
		}else if(idType.equals(CodeTable.CARD_ACCOUNT)){
			return checkIdNo_account(idNumber);
		}else if (idType.equals(CodeTable.CARD_PASSPORT)) {
			return checkIdNo_Passport(idNumber); /* 校验护照号码 */
		} else if (idType.equals(CodeTable.CARD_OFFICER)) {
			return checkIdNo_militaryId(idNumber); /* 校验军官证 */
		} else if (idType.equals(CodeTable.CARD_SOLDIERS)) {
			return checkIdNo_soldierId(idNumber); /* 校验士兵证 */
		} else if (idType.equals(CodeTable.CARD_HK_MACAU_ID)) {
			return checkIdNo_O2(idNumber); /* 校验港澳居民通行证 */
		} else {
			return checkIdNo_O(idNumber); /* 其他证件 */
		}
	}
	/**
	 * 检查证件号码-户口本,规则字母数字汉字6-25个字符
	 * 
	 * @param idNumber
	 * @return
	 */
	public static boolean checkIdNo_account(String idNumber){
		if (StringUtils.isBlank(idNumber)) {
			return false;
		} else {
			String regex = "[\\da-zA-Z\u4E00-\u9FA5]{6,25}";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(idNumber);
			if (!matcher.matches()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查证件号码-中国护照,规则字母数字6-25个字符
	 * 
	 * @param idNumber
	 * @return
	 */
	public static boolean checkIdNo_Passport(String idNumber) {
		if (StringUtils.isBlank(idNumber)) {
			return false;
		} else {
			String regex = "[\\da-zA-Z]{6,25}";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(idNumber);
			if (!matcher.matches()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查证件号码-军官证,规则字母数字汉字6-25个字符
	 * 
	 * @param idNumber
	 *            军官证
	 * @return
	 */
	public static boolean checkIdNo_militaryId(String idNumber) {
		if (StringUtils.isBlank(idNumber)) {
			return false;
		}
		String regex = "[\\da-zA-Z\u4E00-\u9FA5]{6,25}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(idNumber);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 检查证件号码-士兵证,规则字母数字汉字6-25个字符
	 * 
	 * @param idNumber
	 *            士兵证
	 * @return
	 */
	public static boolean checkIdNo_soldierId(String idNumber) {
		if (StringUtils.isBlank(idNumber)) {
			return false;
		}
		String regex = "[\\da-zA-Z\u4E00-\u9FA5]{6,25}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(idNumber);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 检查证件号码-在华居住证,规则字母数字6-25个字符
	 * 
	 * @param idNumber
	 *            在华居住证号码
	 * @return
	 */
	public static boolean checkIdNo_permitInChina(String idNumber) {
		if (StringUtils.isBlank(idNumber)) {
			return false;
		}
		String regex = "[\\da-zA-Z]{6,25}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(idNumber);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 检查证件号码-台胞证身份证,规则字母数字6-25个字符
	 * 
	 * @param idNumber
	 *            台胞证身份证号码
	 * @return
	 */
	public static boolean checkIdNo_O1(String idNumber) {
		if (StringUtils.isBlank(idNumber)) {
			return false;
		}
		String regex = "[\\da-zA-Z]{6,25}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(idNumber);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 检查证件号码-香港身份证,规则字母数字6-25个字符
	 * 
	 * @param idNumber
	 *            香港身份证号码
	 * @return
	 */
	public static boolean checkIdNo_O2(String idNumber) {
		if (StringUtils.isBlank(idNumber)) {
			return false;
		}
		String regex = "[\\da-zA-Z]{6,25}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(idNumber);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 检查证件号码-其他,规则字母数字6-25个字符
	 * 
	 * @param idNumber
	 *            其他号码
	 * @return
	 */
	public static boolean checkIdNo_O(String idNumber) {
		if (StringUtils.isBlank(idNumber)) {
			return false;
		}
		String regex = "[\\da-zA-Z]{6,25}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(idNumber);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 检查手机号码,规则以1开头是十一位数字
	 * 
	 * @param phone
	 *            手机号码
	 * @return
	 */
	public static boolean checkPhone(String phone) {
		if (StringUtils.isBlank(phone)) {
			return false;
		} else {
			phone = phone.trim();
			String regex = "^[1][3|4|5|7|8]\\d{9}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(phone);
			if (!matcher.matches()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查电话号码,规则以1开头是十一位数字
	 * 
	 * @param telNumber   电话号码
	 * @return
	 */
	public static boolean checkTelNumber(String telNumber) {
		telNumber = telNumber.trim();
		String regex = "";
		if (telNumber.length() > 9) {
			regex = "^[0][1-9]{1}[0-9]{1,2}-[0-9]{5,8}$";// 验证带区号的
		} else {
			regex = "^[1-9]{1}[0-9]{5,7}$";// 验证没有区号的
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(telNumber);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 验证港澳台电话号码
	 * @param telNumber
	 * @return
	 */
	public static boolean checkHKNumber(String telNumber){
		if(StringUtils.isBlank(telNumber)){
			return false;
		}
		telNumber = telNumber.trim();
		if(telNumber.length() == 13){
			String head = telNumber.substring(0, 5);
			if(head.equals("00852") || head.equals("00853") || head.equals("00886")){
				return true;
			}
		} else if (telNumber.length() == 8){
			return true;
		}
		return false;
	}

	/**
	 * 查邮政编码，只允许为数字 6位长度
	 * 
	 * @param zipCode     邮政编码
	 * @return
	 */
	public static boolean checkZipCode(String zipCode) {
		if (StringUtils.isBlank(zipCode)) {
			return false;
		} else {
			zipCode = zipCode.trim();
			String regex = "^[0-9]{6}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(zipCode);
			if (!matcher.matches()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查地址
	 * 
	 * @param address
	 *            地址
	 * @return
	 */
	public static boolean checkAddress(String address) {
		if (StringUtils.isNotBlank(address)) {
			String result = "";
			String[] tt = address.split("\\s{1,}"); // 按照空格分隔字符串，多个空格按照一个空格进行分隔
			for (int i = 0; i < tt.length; i++) {
				result = result + tt[i] + " ";
			}
			result = result.trim();// 去掉两边空格
			int addLength = result.length();// 通信地址

			String regex = "^[a-zA-Z0-9#\\-\\(\\)\\u4e00-\\u9fa5 ]+$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(address);
			if (addLength >= 45 || !matcher.matches()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 计算日期（包含临界值）
	 * 
	 * @param targetDate
	 * @param format
	 * @param dateNum
	 * @param unit
	 * @return
	 */
	public static String dateAdd(Date targetDate, String format, int dateNum, String unit) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(format);
		cal.setTime(targetDate);
		if ("Y".equals(unit)) {
			cal.add(Calendar.YEAR, dateNum);
		} else if ("M".equals(unit)) {
			cal.add(Calendar.MONTH, dateNum);
		} else if ("D".equals(unit)) {
			cal.add(Calendar.DATE, dateNum);
		}
		return df.format(cal.getTime());
	}

	/**
	 * 计算日期（不包含临界值）
	 * 
	 * @param targetDate
	 * @param format
	 * @param dateNum
	 * @param unit
	 * @return
	 */
	public static String dateAddNoCriticality(Date targetDate, String format, int dateNum, String unit) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat(format);
		cal.setTime(targetDate);
		if ("Y".equals(unit)) {
			cal.add(Calendar.YEAR, dateNum);
		} else if ("M".equals(unit)) {
			cal.add(Calendar.MONTH, dateNum);
		} else if ("D".equals(unit)) {
			cal.add(Calendar.DATE, dateNum);
		}
		cal.add(Calendar.DATE, 1);
		return df.format(cal.getTime());
	}

	/**
	 * 检查 邮箱格式
	 * 
	 * @param email
	 *            电子邮箱
	 * @return
	 */
	public static boolean checkEmail(String email) {
		if (email.length() > 30) {
			return false;
		} else {
			String regex = "^[0-9a-zA-Z_][-_\\.0-9a-zA-Z-]{0,63}@([0-9a-zA-Z][0-9a-zA-Z-]*\\.)+[a-zA-Z]{2,4}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(email);
			if (!matcher.matches()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断被保人年龄是否符合规则
	 * 
	 * @param birthday
	 *            出生日期
	 * @param ageStart
	 *            年龄开始日期（包含临界日）
	 * @param appStartAgeUnit
	 *            年龄开始单位 Y年 M月 D天
	 * @param ageEnd
	 *            年龄结束日期（包含临界日）
	 * @param appEndAgeUnit
	 *            年龄结束单位 Y年 M月 D天
	 * @param noCriticality
	 *            年龄结束日期是否包含临界日期
	 * @return
	 */
	public static boolean checkAge(String birthday, int ageStart, String appStartAgeUnit, int ageEnd,
			String appEndAgeUnit, boolean noCriticality) {
		try {
			birthday = birthday.replaceAll("-", "").replaceAll("/", "");
			// 以当前日期为基准，计算最小可参保年龄的出生年月日
			String appMinBirthday = BaseRule.dateAdd(new Date(), DateUtil.YYYYMMDD, -ageStart, appStartAgeUnit);
			// 以当前日期为基准，计算最大可参保年龄的出生年月日
			String appMaxBirthday = "";
			if (noCriticality) {
				appMaxBirthday = BaseRule.dateAddNoCriticality(new Date(), DateUtil.YYYYMMDD, -ageEnd, appEndAgeUnit);
			} else {
				appMaxBirthday = BaseRule.dateAdd(new Date(), DateUtil.YYYYMMDD, -ageEnd, appEndAgeUnit);
			}
			Date birthdayDate = DateUtil.convertStringToDate(birthday, DateUtil.YYYYMMDD);
			Date maxDate = DateUtil.convertStringToDate(appMaxBirthday, DateUtil.YYYYMMDD);
			Date minDate = DateUtil.convertStringToDate(appMinBirthday, DateUtil.YYYYMMDD);

			if (birthdayDate.compareTo(minDate) > 0) {
				// BaseError.addError(errorInfo + "年龄必须年满" + ageStart +
				// (appStartAgeUnit.equals("Y") ? "周岁" : "天"));
				return false;
			} else if (birthdayDate.compareTo(maxDate) < 0) {
				// BaseError.addError(errorInfo + "年龄最大必须小于" + ageEnd + "周岁");
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 判断是否成年人
	 * 
	 * @param birthday
	 *            出生日期 yyyy-MM-dd
	 * @param number
	 *            规定成年人周岁临界值
	 * @return
	 */
	public static boolean checkAdult(String birthday, int number) {
		if (birthday == null || "".equals(birthday)) {
			return false;
		}
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthday)) {
			return false;
		} else {
			int age = getAge(birthday);
			if (age < number) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 通过出生日期获取年龄
	 * 
	 * @param birthday
	 *            出生日期
	 * @return
	 */
	public static int getAge(String birthday) {
		Date birth = null;
		birth = DateUtil.convertStringToDate(birthday, DateUtil.YYYY_MM_DD);
		return getAge(birth);
	}

	/**
	 * 通过出生日期获取年龄
	 * 
	 * @param birthday
	 *            出生日期
	 * @return
	 */
	public static int getAge(Date birthday) {
		Calendar cal = Calendar.getInstance();
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		int age = yearNow - yearBirth;
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				age--;
			}
		}
		return age;
	}

	/**
	 * 判断未成年人
	 * 
	 * @param birthday
	 *            出生日期 yyyy-MM-dd
	 * @param number
	 *            规定成年人周岁临界值
	 * @return
	 */
	public static boolean checkJuveniles(String birthday, int number) {
		if (birthday == null || "".equals(birthday)) {
			return false;
		}
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthday)) {
			return false;
		} else {
			int age = getAge(birthday);
			if (age >= number) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 校验在线支付信息的银行卡号
	 * 
	 * @param bankNo
	 * @return
	 */
	public static boolean checkBankNo(String bankNo) {
		if (StringUtils.isBlank(bankNo)) {
			return false;
		} else {
			String regex = "^[A-Za-z0-9]*$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(bankNo);
			if (!matcher.matches() || bankNo.length() < 12 || bankNo.length() > 19) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 校验字符串是否为数字。规则：不为空，可以是整数或者小数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean IsNumber(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		}
		String regex = "^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 保险期间校验。规则：不为空，可以是1-3位数字加d、D、m、M、y、Y任意一个
	 * 
	 * @param insPeriod
	 * @return
	 */
	public static boolean checkInsPeriod(String insPeriod) {
		if (StringUtils.isBlank(insPeriod)) {
			return false;
		}
		String regex = "^[1-9]{1}[\\d]{0,2}[dDmMyY]{1}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(insPeriod);
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 检查证件号码-身份证，规则校验给定的身份证号是否有效、证件号位数、是否含无效字符、出生日期码及校验码
	 * 
	 * @param idNumber
	 *            身份证号码
	 * @param sex
	 *            性别：0-女，1-男
	 * @param birthday
	 *            出生日期 yyyy-MM-dd
	 * @return
	 */
	public static boolean checkIdNo_identityCard(String idNumber, String sex, String birthday) {
		if (StringUtils.isEmpty(idNumber)) {
			return false;
		} else if (!IDCardUtil.verify(idNumber)) {
			return false;
		}
		if (!IDCardUtil.verifySex(idNumber, sex)) {
			return false;
		}
		Date birthdayTemp = DateUtil.convertStringToDate(birthday, DateUtil.YYYY_MM_DD);
		if (!IDCardUtil.verify(idNumber, birthdayTemp)) {
			return false;
		}
		return true;
	}

	/**
	 * 删除银行卡号中的空格
	 * 
	 * @param bankNo
	 * @return
	 */
	public static String deleteTrim(String bankNo) {
		if(StringUtils.isBlank(bankNo)){
			return "";
		}
		return bankNo.replaceAll(" ", "");
	}
	
	/**
	 * 判断是否相等
	 * @return
	 */
	public static void checkEquals(String sessionCode,String code){
		if(StringUtils.isBlank(sessionCode)){
			throw new CommonException("图片验证码丢失请刷新");
		}
		if(StringUtils.isBlank(code)){
			throw new CommonException("请输入图片验证码");
		}
		if(!sessionCode.equals(code)){
			throw new CommonException("请输入正确的图片验证码");
		}
	}
}