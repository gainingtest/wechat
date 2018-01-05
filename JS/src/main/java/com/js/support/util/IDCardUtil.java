package com.js.support.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

public class IDCardUtil {

	private static final Map<String, String> cityCodes; // 城市编码

	public static final int CHINA_ID_MIN_LENGTH = 15; // 中国公民身份证号码最小长度
	public static final int CHINA_ID_MAX_LENGTH = 18; // 中国公民身份证号码最大长度
	public static final int MIN = 1930; // 最低年限
	
	/** 每位加权因子 */
	public static final int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	/** 第18位校检码 */
	public static final String verifyCode[] = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };

	static {
		cityCodes = new HashMap<String, String>();
		cityCodes.put("11", "北京");
		cityCodes.put("12", "天津");
		cityCodes.put("13", "河北");
		cityCodes.put("14", "山西");
		cityCodes.put("15", "内蒙古");
		cityCodes.put("21", "辽宁");
		cityCodes.put("22", "吉林");
		cityCodes.put("23", "黑龙江");
		cityCodes.put("31", "上海");
		cityCodes.put("32", "江苏");
		cityCodes.put("33", "浙江");
		cityCodes.put("34", "安徽");
		cityCodes.put("35", "福建");
		cityCodes.put("36", "江西");
		cityCodes.put("37", "山东");
		cityCodes.put("41", "河南");
		cityCodes.put("42", "湖北");
		cityCodes.put("43", "湖南");
		cityCodes.put("44", "广东");
		cityCodes.put("45", "广西");
		cityCodes.put("46", "海南");
		cityCodes.put("50", "重庆");
		cityCodes.put("51", "四川");
		cityCodes.put("52", "贵州");
		cityCodes.put("53", "云南");
		cityCodes.put("54", "西藏");
		cityCodes.put("61", "陕西");
		cityCodes.put("62", "甘肃");
		cityCodes.put("63", "青海");
		cityCodes.put("64", "宁夏");
		cityCodes.put("65", "新疆");
		cityCodes.put("71", "台湾");
		cityCodes.put("81", "香港");
		cityCodes.put("82", "澳门");
		cityCodes.put("91", "国外");
	}

	/**
	 * 验证身份证是否合法
	 */
	public static boolean validateCard(String idCard) {
		if(idCard == null || idCard.equals("")){
			return false;
		}
		String card = idCard.trim();
		// 验证18位身份编码是否合法
		if (validateIdCard18(card)) {
			return true;
		}
		// 验证15位身份编码是否合法
		if (validateIdCard15(card)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取性别信息
	 * @param sex  性别
	 * @param idType 证件类型
	 * @param idNo  证件号码
	 * @return
	 */
	public static String getSex(String sex, String idType, String idNo) {
		String customeSex = null;
		// 性别业务逻辑：如果性别是空值，并且是身份证类型，是就获取身份证中得性别。
		if (StringUtils.isBlank(sex)) {
			customeSex = "N";
			if (idType != null && "0".equals(idType) && StringUtils.isNotBlank(idNo)) {
				customeSex = getGenderByIdCard(idNo);
			}
		} else {
			customeSex = sex;
		}
		return customeSex;
	}

	/**
	 * 获取出生日期
	 */
	public static String getBirthDay(String birthDay, String idType, String idNo) {
		// 生日业务逻辑：如果生日是空值，那么判断证件类型是身份证类型，是就获取身份证中得生日。
		String customeBirthDay = null;
		if (StringUtils.isBlank(birthDay)) {
			if (idType != null && "0".equals(idType) && StringUtils.isNotBlank(idNo)) {
				String tempBirth = getBirthByIdCard(idNo);
				if (tempBirth != null) {
					customeBirthDay = tempBirth;
				}
			}
		} else {
			customeBirthDay = birthDay;
		}
		return customeBirthDay;
	}

	/**
	 * 根据身份编号获取性别
	 * 
	 * @param idCard    身份编号
	 * @return 性别(0-男，1-女，2-未知)
	 */
	public static String getGenderByIdCard(String idCard) {
		String sGender = "2";
		if (idCard.length() == CHINA_ID_MIN_LENGTH) {
			idCard = conver15CardTo18(idCard);
		}
		if (!validateIdCard18(idCard)) {
			return sGender;
		}
		String sCardNum = idCard.substring(16, 17);
		if (Integer.parseInt(sCardNum) % 2 != 0) {
			sGender = "0";
		} else {
			sGender = "1";
		}
		return sGender;
	}

	/**
	 * 根据身份编号获取生日
	 * 
	 * @param idCard    身份编号
	 * @return 生日(yyyyMMdd)
	 */
	public static String getBirthByIdCard(String idCard) {
		int len = idCard.length();
		if (len < CHINA_ID_MIN_LENGTH) {
			return null;
		} else if (len == CHINA_ID_MIN_LENGTH) {
			idCard = conver15CardTo18(idCard);
		}
		if (!validateIdCard18(idCard)) {
			return null;
		}
		String birth = idCard.substring(6, 14);
		birth = birth.substring(0, 4) + "-" + birth.substring(4, 6) + "-" + birth.substring(6, 8);
		return birth;
	}

	/**
	 * 校验出生日期是否合法
	 * 
	 * @param: birthday出生日期（必须符合yyyy-MM-dd）
	 * @return: boolean
	 * @createDate: 2013-4-23
	 */
	public static boolean validateBirthday(String birthday) {
		boolean validateFlag = false;
		// 正则表达式校验
		if (birthday != null && birthday.trim().length() == 10) {
			Pattern pattern = Pattern
					.compile("(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)");
			Matcher matcher = pattern.matcher(birthday);
			validateFlag = matcher.matches();
		}
		return validateFlag;
	}
	
	/**
	 * 验证身份证性别
	 * @param idNumber   受检身份证号
	 * @param sexString  性别
	 * @return 如果sexString为Null，或者身份证号的性别码%2与给定的性别码相同时返回True，否则返回False。
	 *         给定的性别码：1男0女
	 */
	public static boolean verifySex(String idNumber, String sexString) {
		if (StringUtils.isBlank(idNumber) || StringUtils.isBlank(sexString)) {
			return false;
		}
		String cardsex = idNumber.length() == 18 ? idNumber.substring(16, 17)
				: idNumber.substring(14, 15);
		int sex_i = Integer.parseInt(cardsex) % 2;
		if ((sex_i == 0 && BaseRule.SEX_FEMALE.equals(sexString))
				|| (sex_i == 1 && BaseRule.SEX_MALE.equals(sexString))) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 校验给定的身份证号是否有效。会从证件号位数，是否含无效字符，出生日期码及校验码几方面加以验证。
	 * @param idNumber  受检身份证号
	 * @return
	 */
	public static boolean verify(String idNumber) {
		// 转换身份证号为大写
		idNumber = idNumber.toUpperCase();
		// 判断身份证位数是否为空
		if (idNumber == null) {
			return false;
		}
		// 判断身份证位数是否为15位或者18位
		if (idNumber.length() != 15 && idNumber.length() != 18) {
			return false;
		}
		// 长度符合要求，判断除末位外，其它位是否为数字
		for (int i = 0; i < idNumber.length(); i++) {
			char c = idNumber.charAt(i);
			if (i < 17 && !CharUtils.isAsciiNumeric(c)) {
				return false;
			}
		}
		// 判断末位是否为数字或者'X'
		if (idNumber.length() == 18 && idNumber.charAt(17) != 'X'
			&& !CharUtils.isAsciiNumeric(idNumber.charAt(17))) {
			return false;
		}
		// 截取出生日期
		String dateStr = idNumber.length() == 18 ? idNumber.substring(6, 14)
			: idNumber.substring(6, 12);
			// 校验身份证的出生日期是否合法
		return isValidDate(dateStr) && verifyCheckDigit(idNumber);
	}

	/**
	 * 校验给定的身份证号的出生日期码所代表的日期与给定的出生日期是否相同。
	 * @param idNumber   受检身份证号
	 * @param birthDate  出生日期
	 * @return   如果birthDate为Null，或者身份证号的出生日期码所代表的日期与给定的出生日期相同时返回True，否则返回False。
	 */
	public static boolean verify(String idNumber, Date birthDate) {
		if (StringUtils.isBlank(idNumber) || birthDate == null) {
			return false;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(birthDate);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int date = cal.get(Calendar.DATE);
		if (idNumber.length() == 18) {
			String yearStr = idNumber.substring(6, 10);
			String monthStr = idNumber.substring(10, 12);
			String dateStr = idNumber.substring(12, 14);
			try {
				return (Integer.parseInt(dateStr) == date)
						&& (Integer.parseInt(monthStr) == month)
						&& (Integer.parseInt(yearStr) == year);
			} catch (Exception e) {
				return false;
			}
		} else if (idNumber.length() == 15) {
			String yearStr = idNumber.substring(6, 8);
			String monthStr = idNumber.substring(8, 10);
			String dateStr = idNumber.substring(10, 12);
			try {
				return (Integer.parseInt(dateStr) == date)
						&& (Integer.parseInt(monthStr) == month)
						&& (Integer.parseInt(yearStr) == (year % 100));
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 判断日期是否合法
	 * @param dateString
	 * @return
	 */
	public static boolean isValidDate(String dateString) {
		if (StringUtils.isBlank(dateString)) {
			return false;
		}
		String pattern = "yyyyMMdd";
		if (dateString.length() == 6) {
			pattern = "yyMMdd";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		try {
			Date birthDate = dateFormat.parse(dateString);
			String birthDateStr = dateFormat.format(birthDate);
			return birthDateStr.equals(dateString);
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 检验给定身份证号的校验码是否正确
	 * @param idNumber    受检身份证号
	 * @return 如果是15位证号，或者是校验码正确的18位证号返回True；否则返回False
	 */
	public static boolean verifyCheckDigit(String idNumber) {
		if (StringUtils.isBlank(idNumber)) {
			return false;
		}
		if (idNumber.length() == 15) {
			return true;
		}
		if (idNumber.length() == 18) {
			char checkDigit = idNumber.charAt(17);
			return checkDigit == checkDigit(idNumber.toCharArray());
		}
		return false;
	}
	
	/**
	 * @param id18
	 * @return
	 */
	protected static char checkDigit(char[] id18) {
		char[] code = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' }; // 11个
		int sum = 0;
		for (int i = 0; i < 17; i++) {
			sum = sum + (CharUtils.toIntValue(id18[i])) * wi(i);
		}
		int index = sum % 11;
		return code[index];
	}
	
	protected static int wi(int index) {
		if (index > 17 || index < 0)
			throw new IllegalArgumentException("index out of bound");
		int n = 17 - index;
		return (1 << n) % 11;
	}
	
	/**
	 * 将15位身份证号码转换为18位
	 * 
	 * @param idCard   15位身份编码
	 * @return 18位身份编码
	 */
	public static String conver15CardTo18(String idCard) {
		String idCard18 = "";
		if (idCard.length() != CHINA_ID_MIN_LENGTH) {
			return null;
		}
		if (isNum(idCard)) {
			// 获取出生年月日
			String birthday = idCard.substring(6, 12);
			Date birthDate = null;
			try {
				birthDate = new SimpleDateFormat("yyMMdd").parse(birthday);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar cal = Calendar.getInstance();
			if (birthDate != null)
				cal.setTime(birthDate);
			// 获取出生年(完全表现形式,如：2010)
			String sYear = String.valueOf(cal.get(Calendar.YEAR));
			idCard18 = idCard.substring(0, 6) + sYear + idCard.substring(8);
			// 转换字符数组
			char[] cArr = idCard18.toCharArray();
			if (cArr != null) {
				int[] iCard = converCharToInt(cArr);
				int iSum17 = getPowerSum(iCard);
				// 获取校验位
				String sVal = getCheckCode18(iSum17);
				if (sVal.length() > 0) {
					idCard18 += sVal;
				} else {
					return null;
				}
			}
		} else {
			return null;
		}
		return idCard18;
	}

	/**
	 * 验证18位身份编码是否合法
	 * 
	 * @param idCard   身份编码
	 * @return 是否合法
	 */
	public static boolean validateIdCard18(String idCard) {
		boolean bTrue = false;
		if (idCard.length() == CHINA_ID_MAX_LENGTH) {
			// 前17位
			String code17 = idCard.substring(0, 17);
			// 第18位
			String code18 = idCard.substring(17, CHINA_ID_MAX_LENGTH);
			if (isNum(code17)) {
				char[] cArr = code17.toCharArray();
				if (cArr != null) {
					int[] iCard = converCharToInt(cArr);
					int iSum17 = getPowerSum(iCard);
					// 获取校验位
					String val = getCheckCode18(iSum17);
					if (val.length() > 0) {
						if (val.equalsIgnoreCase(code18)) {
							// 验证出生日期是否合法
							String birthday = idCard.substring(6, 14);
							birthday = birthday.substring(0, 4) + "-" + birthday.substring(4, 6) + "-" + birthday.substring(6, 8);
							bTrue = validateBirthday(birthday);
						}
					}
				}
			}
		}
		return bTrue;
	}

	/**
	 * 验证15位身份编码是否合法
	 * 
	 * @param idCard  身份编码
	 * @return 是否合法
	 */
	public static boolean validateIdCard15(String idCard) {
		if (idCard.length() != CHINA_ID_MIN_LENGTH) {
			return false;
		}
		if (isNum(idCard)) {
			String proCode = idCard.substring(0, 2);
			if (cityCodes.get(proCode) == null) {
				return false;
			}
			String birthCode = idCard.substring(6, 12);
			Date birthDate = null;
			try {
				birthDate = new SimpleDateFormat("yy").parse(birthCode.substring(0, 2));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar cal = Calendar.getInstance();
			if (birthDate != null)
				cal.setTime(birthDate);
			if (!valiDate(cal.get(Calendar.YEAR), Integer.valueOf(birthCode.substring(2, 4)).intValue(), Integer.valueOf(birthCode.substring(4, 6))
					.intValue())) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * 验证小于当前日期 是否有效
	 * 
	 * @param iYear    待验证日期(年)
	 * @param iMonth   待验证日期(月 1-12)
	 * @param iDate    待验证日期(日)
	 * @return 是否有效
	 */
	public static boolean valiDate(int iYear, int iMonth, int iDate) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int datePerMonth;
		if (iYear < MIN || iYear >= year) {
			return false;
		}
		if (iMonth < 1 || iMonth > 12) {
			return false;
		}
		switch (iMonth) {
		case 4:
		case 6:
		case 9:
		case 11:
			datePerMonth = 30;
			break;
		case 2:
			boolean dm = ((iYear % 4 == 0 && iYear % 100 != 0) || (iYear % 400 == 0)) && (iYear > MIN && iYear < year);
			datePerMonth = dm ? 29 : 28;
			break;
		default:
			datePerMonth = 31;
		}
		return (iDate >= 1) && (iDate <= datePerMonth);
	}

	/**
	 * 数字验证
	 * 
	 * @param val
	 * @return 提取的数字。
	 */
	public static boolean isNum(String val) {
		return val == null || "".equals(val) ? false : val.matches("^[0-9]*$");
	}

	/**
	 * 将字符数组转换成数字数组
	 * 
	 * @param ca    字符数组
	 * @return 数字数组
	 */
	public static int[] converCharToInt(char[] ca) {
		int len = ca.length;
		int[] iArr = new int[len];
		try {
			for (int i = 0; i < len; i++) {
				iArr[i] = Integer.parseInt(String.valueOf(ca[i]));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return iArr;
	}

	/**
	 * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
	 * 
	 * @param iArr
	 * @return 身份证编码。
	 */
	public static int getPowerSum(int[] iArr) {
		int iSum = 0;
		if (power.length == iArr.length) {
			for (int i = 0; i < iArr.length; i++) {
				for (int j = 0; j < power.length; j++) {
					if (i == j) {
						iSum = iSum + iArr[i] * power[j];
					}
				}
			}
		}
		return iSum;
	}

	/**
	 * 将power和值与11取模获得余数进行校验码判断
	 * 
	 * @param iSum
	 * @return 校验位
	 */
	public static String getCheckCode18(int iSum) {
		String sCode = "";
		switch (iSum % 11) {
		case 10:
			sCode = "2";
			break;
		case 9:
			sCode = "3";
			break;
		case 8:
			sCode = "4";
			break;
		case 7:
			sCode = "5";
			break;
		case 6:
			sCode = "6";
			break;
		case 5:
			sCode = "7";
			break;
		case 4:
			sCode = "8";
			break;
		case 3:
			sCode = "9";
			break;
		case 2:
			sCode = "x";
			break;
		case 1:
			sCode = "0";
			break;
		case 0:
			sCode = "1";
			break;
		}
		return sCode;
	}
	
	/**
	 * 证件类型转换
	 * @param inIdType
	 * @return
	 */
	public static String getIdType(String inIdType){
		String outIdType = "";
		if ("01".equals(inIdType)) {
			outIdType = "0";
		} else if ("02".equals(inIdType)) {
			outIdType = "1";
		} else if ("07".equals(inIdType)) {
			outIdType = "2";
		} else if ("04".equals(inIdType)) {
			outIdType = "3";
		} else if ("12".equals(inIdType)) {
			outIdType = "4";
		} else if ("99".equals(inIdType)) {
			outIdType = "7";
		} else if ("13".equals(inIdType)) {
			outIdType = "A";
		} else if ("09".equals(inIdType)) {
			outIdType = "B";
		} else if ("11".equals(inIdType)) {
			outIdType = "6";
		} else if ("05".equals(inIdType)) {
			outIdType = "05";
		} else if ("06".equals(inIdType)) {
			outIdType = "06";
		} else if ("08".equals(inIdType)) {
			outIdType = "08";
		} else if ("10".equals(inIdType)) {
			outIdType = "10";
		} else if ("51".equals(inIdType)) {
			outIdType = "51";
		} else if ("52".equals(inIdType)) {
			outIdType = "52";
		} else if ("53".equals(inIdType)) {
			outIdType = "53";
		} else if ("98".equals(inIdType)) {
			outIdType = "98";
		} else if ("03".equals(inIdType)) {
			outIdType = "03";
		} else {
			outIdType = "7";
		}
		return outIdType;
	}
}