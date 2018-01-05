package com.js.support.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.js.support.util.CodeTable;

/**
 * 描述：日期工具类<br>
 */
public class DateUtil {
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_year_MM_month_DD_day = "yyyy年MM月dd日";
	public static final String HHMMSS = "HH:mm:ss";
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
	public static final String YEAR = "year";
	public static final String MONTH = "month";
	public static final String DAY = "day";
	
	public static final SimpleDateFormat yyyy_mm_dd_Format = new SimpleDateFormat(YYYY_MM_DD);
	public static final SimpleDateFormat yyyymmdd_Format = new SimpleDateFormat(YYYYMMDD);
	
	/**
	 * 描述：字符串转换时间类型 作者: LIFEILONG 修改日期：2014-07-02下午03:13:35
	 * 
	 * @param str  时间字符串
	 * @param dateFormat   格式 "yyyy-MM-dd"
	 * @return
	 */
	public static Date convertStringToDate(String str, String dateFormat) {
		if (StringUtils.isNotBlank(str)) {
			SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
			try {
				Date birthDate = sf.parse(str);
				return birthDate;
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * 描述：获取当前日期 作者: LIFEILONG 修改日期：2014-07-02上午10:22:50
	 * 
	 * @return 返回当前日期 日期格式 yyyy-MM-dd
	 */
	public static String currentDate() {
		return currentDate(YYYY_MM_DD);
	}

	/**
	 * 描述：获取当前日期 作者: LIFEILONG 修改日期：2014-07-02上午10:23:26
	 * 
	 * @param dateFormat    日期格式
	 * @return 返回当前日期
	 */
	public static String currentDate(String dateFormat) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
			String currentDate = sf.format(new Date());
			return currentDate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 描述：把java.util.date 转换成String类型 作者: LIFEILONG 修改日期：2014-07-02上午10:46:04
	 * 
	 * @param date     日期
	 * @return 格式化成yyyy-MM-dd格式的字符串类型日期
	 */
	public static String convertDate(Date date) {
		if (date == null) {
			return null;
		}
		return convertDate(date, YYYY_MM_DD);
	}

	/**
	 * 描述：把java.util.date 转换成String类型 作者: LIFEILONG 修改日期：2014-07-02上午10:46:56
	 * 
	 * @param date   日期
	 * @param dateFormat    日期格式
	 * @return 格式化成特定格式的字符串类型日期
	 */
	public static String convertDate(Date date, String dateFormat) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
			String currentDate = sf.format(date);
			return currentDate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取当前日期的前一天
	 * @param dateFormat
	 * @return
	 */
	public static String getBeforeDayDate(String dateFormat) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			Date date = calendar.getTime();
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 描述：获取当前日期的后一天 date类型<br>
	 * 作者: LIFEILONG<br>
	 * 修改日期：2014-07-02上午10:46:56
	 * 
	 * @return 格式化成特定格式的字符串类型日期
	 */
	public static Date getNextDayDate() {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			return calendar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 描述：获取当前日期的后一天 yyyy-MM-dd格式<br>
	 * 作者: LIFEILONG<br>
	 * 修改日期：2014-07-02上午10:46:56
	 * 
	 * @return 格式化成特定格式的字符串类型日期
	 */
	public static String getNextDay() {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date date = calendar.getTime();
			return yyyy_mm_dd_Format.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 描述：获取当前日期的后一天（传入日期格式）<br>
	 * 
	 * @param dateFormat    日期格式<br>
	 * 
	 * 作者: LIFEILONG<br>
	 * 修改日期：2014-07-02上午10:46:56
	 * 
	 * @return 格式化成特定格式的字符串类型日期
	 */
	public static String getNextDay(String dateFormat) {
		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date date = calendar.getTime();
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 描述：获取当前日期的下一年(yyyy-MM-dd格式)<br>
	 * 
	 * 作者: LIFEILONG<br>
	 * 修改日期：2014-07-02上午10:46:56<br>
	 * 
	 * @return 格式化成特定格式的字符串类型日期
	 */
	public static String getNextYear() {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, 1);
			Date date = calendar.getTime();
			return yyyy_mm_dd_Format.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 描述：获取当前日期的下一年<br>
	 * 
	 * @param dateFormat
	 *            日期格式<br>
	 *            作者: LIFEILONG<br>
	 *            修改日期：2014-07-02上午10:46:56<br>
	 * 
	 * @return 格式化成特定格式的字符串类型日期
	 */
	public static String getNextYear(String dateFormat) {
		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			calendar.add(Calendar.YEAR, 1);
			Date date = calendar.getTime();
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 描述：获取指定日期的下一年(yyyy-MM-dd格式)<br>
	 * 
	 * 日期格式<br>
	 * 作者: LIFEILONG<br>
	 * 修改日期：2014-07-02上午10:46:56<br>
	 * 
	 */
	public static String getNextYearByDate(String date) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(yyyy_mm_dd_Format.parse(date));
			calendar.add(Calendar.YEAR, 1);
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			return yyyy_mm_dd_Format.format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取指定日期相差N天的日期(yyyy-MM-dd格式)
	 * @param date 指定日期
	 * @param days 相差天数，正数是向前N天，负数是向后N天
	 * @return
	 */
	public static String getAfterDays(String date,int days) {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(yyyy_mm_dd_Format.parse(date));
			calendar.add(Calendar.DAY_OF_YEAR, days);
			return yyyy_mm_dd_Format.format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 描述：获取当前时间 作者: LIFEILONG 修改日期：2014-07-02上午11:09:20
	 * 
	 * @return
	 */
	public static Date getDate() {
		Date date = new Date();
		return date;
	}

	/**
	 * 描述：将传入的时间转化成秒数 作者: LIFEILONG 修改日期：2014-07-02下午19:09:20
	 * 
	 * @return 秒数
	 */
	public static int getSecond(String value) {
		StringTokenizer s = new StringTokenizer(value, ":");
		int type = s.countTokens() - 1;
		int result = 0;
		Pattern p = Pattern.compile(":");
		if (type == 0)// ss
			result = Integer.parseInt(value);
		if (type == 1) {// mm:ss
			String strValue[] = p.split(value);
			result = Integer.parseInt(strValue[0]) * 60 + Integer.parseInt(strValue[1]);
		}
		if (type == 2) {// hh:mm:ss
			String strValue[] = p.split(value);
			result = Integer.parseInt(strValue[0]) * 3600 + Integer.parseInt(strValue[1]) * 60 + Integer.parseInt(strValue[2]);
		}
		return result;
	}

	/**
	 * 描述：根据日期获取年、月、日 作者: LIFEILONG 修改日期：2014-07-02下午03:18:40
	 * 
	 * @param birthday
	 * @param flag
	 * @return
	 */
	public static String getNumByBirthday(Date birthday, String flag) {
		String Num = null;
		String birthdayStr = convertDate(birthday, YYYY_MM_DD);
		if (StringUtils.isNotBlank(birthdayStr)) {
			String[] birthdaySplit = birthdayStr.split("-");
			if (birthdaySplit != null) {
				if (birthdaySplit.length == 3) {
					if (YEAR.equals(flag)) {
						Num = birthdaySplit[0];
					}
					if (MONTH.equals(flag)) {
						Num = birthdaySplit[1];
					}
					if (DAY.equals(flag)) {
						Num = birthdaySplit[2];
					}
				}
			}
		}
		return Num;
	}

	/**
	 * 描述：转换日期格式，把日期字符串从yyyy-MM-dd格式转换成toDateFormat格式 作者: LIFEILONG
	 * 修改日期：2014-07-02上午10:46:56
	 * 
	 * @param dateString  日期
	 * @param toDateFormat   日期格式
	 * @return 格式化成特定格式的字符串类型日期
	 */
	public static String convertDate(String dateString, String toDateFormat) {
		return convertDate(dateString, YYYY_MM_DD, toDateFormat);
	}

	/**
	 * 描述：转换日期格式，把日期字符串从fromDateFormat格式转换成toDateFormat格式 作者: LIFEILONG
	 * 修改日期：2014-07-02上午10:46:56
	 * 
	 * @param dateString    日期
	 * @param fromDateFormat  日期格式
	 * @param toDateFormat    日期格式
	 * @return 格式化成特定格式的字符串类型日期
	 */
	public static String convertDate(String dateString, String fromDateFormat, String toDateFormat) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat(fromDateFormat);
			Date date = sf.parse(dateString);
			SimpleDateFormat sf1 = new SimpleDateFormat(toDateFormat);
			String currentDate = sf1.format(date);
			return currentDate;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 描述：把时间戳转化成指定格式的时间 作者: LIFEILONG 修改日期：2014-07-02上午10:46:56
	 * 
	 * @param timestamp  时间戳
	 * @param toDateFormat   转化成的时间格式
	 * @return 指定格式的日期字符串
	 */
	public static String getTimeFromTimestamp(String timestamp, String toDateFormat) {
		try {
			// 时间戳转化为Sting或Date
			SimpleDateFormat format = new SimpleDateFormat(toDateFormat);
			Long time = new Long(timestamp);
			String d = format.format(new Date(time * 1000));
			return d;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 描述：把时间戳转化成时间 作者: LIFEILONG 修改日期：2014-07-02上午10:46:56
	 * 
	 * @param timestamp   时间戳
	 * @return 日期
	 */
	public static Date getTimeFromTimestamp(String timestamp) {
		try {
			// 时间戳转化为Date
			Long time = new Long(timestamp);
			return new Date(time * 1000);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 如果是年份加减cal.add(1, i); 
	 * 如果是月份加减cal.add(2, i);
	 * 如果是星期加减cal.add(3, i); 
	 * 如果是每日加减cal.add(5, i);
	 * 如果是小时加减cal.add(10, i);
	 * 如果是分钟加减cal.add(12, i);
	 * 如果是秒的加减cal.add(13, i);
	 * @param date
	 * @param i 
	 * @param type 
	 * @return
	 * @throws Exception
	 */
	public static Date addOrMinusYear(Date date, int type, int i){
		Date rtn = null;
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(type, i);
		rtn = cal.getTime();
		return rtn;
	}
	
	/**
	 *  日期格式yyyyMMdd转成yyyy-MM-dd 
	 * @param date
	 * @return
	 */
	public static String daySeparate(String date) {
		String newDate = "";
		if (StringUtils.isBlank(date)) {
			return newDate;
		}
		if(date.length() == 8){
			try {
				Date beforeDate = yyyymmdd_Format.parse(date);
				newDate = yyyy_mm_dd_Format.format(beforeDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			return date;
		}
		return newDate;
	}
	
	/**
	 * 把日期字符串中的“-”去掉
	 * @param date
	 * @return
	 */
	public static String dayNoSeparate(String date) {
		if(StringUtils.isNotBlank(date)){
			return date.replace("-", CodeTable.IS_NULL2);
		}
		return CodeTable.IS_NULL2;
	}
	
	/**
	 * 日期格式yyyyMMddhhmmss转成yyyy-MM-dd hh:mm:ss
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String dayTimeSeparate(String date) {
		String newDate = "";
		if(date.length() == 14){
			SimpleDateFormat before = new SimpleDateFormat(YYYYMMDDHHMMSS);
			try {
				Date beforeDate = before.parse(date);
				SimpleDateFormat after = new SimpleDateFormat(YMDHMS);
				newDate = after.format(beforeDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			return date;
		}
		return newDate;
	}
	
	/**
	 * 获得指定月份的日期
	 * @param date 日期
	 * @param n    前后几个月的时间
	 * @return
	 */
	public static String getAppointMonth(Date date, int n){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); // 设置时间为当前时间
		calendar.add(Calendar.MONTH, n);// 月份减1
		Date resultDate = calendar.getTime(); // 结果
		return yyyy_mm_dd_Format.format(resultDate);
	}

	/**
	 * 查询两个日期相隔天数
	 * @param Startdate
	 * @param Enddate
	 * @return
	 * @throws ParseException
	 */
	public static int DateDifference(String Startdate, String Enddate) throws ParseException {
//		SimpleDateFormat newdata = new SimpleDateFormat(YYYY_MM_DD);
		Date smdate = yyyy_mm_dd_Format.parse(Startdate);
		Date bdate = yyyy_mm_dd_Format.parse(Enddate);
//		SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
		smdate = yyyy_mm_dd_Format.parse(yyyy_mm_dd_Format.format(smdate));
		bdate = yyyy_mm_dd_Format.parse(yyyy_mm_dd_Format.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		int parseInt = Integer.parseInt(String.valueOf(between_days));
		return parseInt;
	}
	
	/**
	 * 描述：计算两个时间相差的秒数 作者: yinshuhan 修改日期：2016-05-30
	 * 
	 */
	public static long seconddiffer(Date date1, Date date2){ 
		//return date1.getTime() / (24*60*60*1000) - date2.getTime() / (24*60*60*1000); 
		long differ = date2.getTime() - date1.getTime();//用立即数，减少乘法计算的开销
		return differ;  
	}
	/**
	 * 描述：计算两个日期相差的天数 作者: yinshuhan 修改日期：2016-05-18
	 * 
	 */
	public static long differ(Date date1, Date date2){
		//return date1.getTime() / (24*60*60*1000) - date2.getTime() / (24*60*60*1000); 
		long differ = date2.getTime()/86400000 - date1.getTime()/86400000;//用立即数，减少乘法计算的开销
		return differ;
	}
	
}