/**
 * @FileName: DateUtil.java
 * @Package com.imxiaomai.framework.util
 * 
 * @author warship
 * @created 2013年11月10日 上午12:25:42
 * 
 * Copyright 2013-2050 小麦公社 版权所有
 */
package com.mall.admin.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 日期工具类
 * </p>
 * 
 * @author warship
 * @since 1.0
 * @version 1.0
 */
public final class DateUtil {

	private static final Map<String, ThreadLocal<SimpleDateFormat>> timestampFormatPool = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

	private static final Map<String, ThreadLocal<SimpleDateFormat>> dateFormatPool = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

	private static final Object timestampFormatLock = new Object();

	private static final Object dateFormatLock = new Object();

	private static String dateFormatPattern = "yyyy-MM-dd";

	private static String dateFormatPattern_CN = "yyyy年MM月dd日";

	private static String timestampPattern = "yyyy-MM-dd HH:mm:ss";

	private static String simplifyDatePattern = "yyyyMMdd";

	private static String simplifyTimestampPattern = "yyyyMMddHHmmss";

	private static String timestampPatternCps = "yyyyMMddHHmmss";

	private static String timestampPatternSplit = "yyyyMMddHHmm";

	/**
	 * "10:00~12:00" -> tomorow {"beginTime":1423188000000,
	 * "endTime"->"1423195200000"}
	 * 
	 * @param timeSplit
	 * @return
	 * @throws ParseException
	 */
	public static Map<String, Long> timeSplit2Millis(String timeSplit) throws ParseException {
		Calendar cal = Calendar.getInstance();
		HashMap<String, Long> result = new HashMap<String, Long>();
		String[] timeSplits = timeSplit.split("-");
		String beginTime = timeSplits[0];
		String endTime = timeSplits[1];

		SimpleDateFormat sf = new SimpleDateFormat(DateUtil.simplifyDatePattern);
		Date date = new Date();
		String beginDate = sf.format(date) + beginTime.replace(":", "");
		String endDate = sf.format(date) + endTime.replace(":", "");

		sf = new SimpleDateFormat(DateUtil.timestampPatternSplit);
		cal.setTime(sf.parse(beginDate));
		cal.add(Calendar.DAY_OF_YEAR, +1);
		beginDate = sf.format(cal.getTime());

		cal.setTime(sf.parse(endDate));
		cal.add(Calendar.DAY_OF_YEAR, +1);
		endDate = sf.format(cal.getTime());

		cal.setTime(sf.parse(beginDate));
		result.put("beginTime", cal.getTimeInMillis());

		cal.setTime(sf.parse(endDate));
		result.put("endTime", cal.getTimeInMillis());
		return result;
	}

	private static SimpleDateFormat getDateFormatCN() {
		ThreadLocal<SimpleDateFormat> tl = dateFormatPool.get(dateFormatPattern_CN);
		if (null == tl) {
			synchronized (dateFormatLock) {
				tl = dateFormatPool.get(dateFormatPattern_CN);
				if (null == tl) {
					tl = new ThreadLocal<SimpleDateFormat>() {
						@Override
						protected synchronized SimpleDateFormat initialValue() {
							return new SimpleDateFormat(dateFormatPattern_CN);
						}
					};
					dateFormatPool.put(dateFormatPattern_CN, tl);
				}
			}
		}
		return tl.get();
	}

	private static SimpleDateFormat getDateFormat() {
		ThreadLocal<SimpleDateFormat> tl = dateFormatPool.get(dateFormatPattern);
		if (null == tl) {
			synchronized (dateFormatLock) {
				tl = dateFormatPool.get(dateFormatPattern);
				if (null == tl) {
					tl = new ThreadLocal<SimpleDateFormat>() {
						@Override
						protected synchronized SimpleDateFormat initialValue() {
							return new SimpleDateFormat(dateFormatPattern);
						}
					};
					dateFormatPool.put(dateFormatPattern, tl);
				}
			}
		}
		return tl.get();
	}

	public static SimpleDateFormat getTimestampFormat() {
		ThreadLocal<SimpleDateFormat> tl = timestampFormatPool.get(timestampPattern);
		if (null == tl) {
			synchronized (timestampFormatLock) {
				tl = timestampFormatPool.get(timestampPattern);
				if (null == tl) {
					tl = new ThreadLocal<SimpleDateFormat>() {
						@Override
						protected synchronized SimpleDateFormat initialValue() {
							return new SimpleDateFormat(timestampPattern);
						}
					};
					timestampFormatPool.put(timestampPattern, tl);
				}
			}
		}
		return tl.get();
	}

	private static SimpleDateFormat getSimplifyTimestampFormat() {
		ThreadLocal<SimpleDateFormat> tl = timestampFormatPool.get(simplifyTimestampPattern);
		if (null == tl) {
			synchronized (timestampFormatLock) {
				tl = timestampFormatPool.get(simplifyTimestampPattern);
				if (null == tl) {
					tl = new ThreadLocal<SimpleDateFormat>() {
						@Override
						protected synchronized SimpleDateFormat initialValue() {
							return new SimpleDateFormat(simplifyTimestampPattern);
						}
					};
					timestampFormatPool.put(simplifyTimestampPattern, tl);
				}
			}
		}
		return tl.get();
	}

	private static SimpleDateFormat getSimplifyDateFormat() {
		ThreadLocal<SimpleDateFormat> tl = timestampFormatPool.get(simplifyDatePattern);
		if (null == tl) {
			synchronized (timestampFormatLock) {
				tl = timestampFormatPool.get(simplifyDatePattern);
				if (null == tl) {
					tl = new ThreadLocal<SimpleDateFormat>() {
						@Override
						protected synchronized SimpleDateFormat initialValue() {
							return new SimpleDateFormat(simplifyDatePattern);
						}
					};
					timestampFormatPool.put(simplifyDatePattern, tl);
				}
			}
		}
		return tl.get();
	}

	public static SimpleDateFormat getTimestampFormatCps() {
		ThreadLocal<SimpleDateFormat> tl = timestampFormatPool.get(timestampPatternCps);
		if (null == tl) {
			synchronized (timestampFormatLock) {
				tl = timestampFormatPool.get(timestampPatternCps);
				if (null == tl) {
					tl = new ThreadLocal<SimpleDateFormat>() {
						@Override
						protected synchronized SimpleDateFormat initialValue() {
							return new SimpleDateFormat(timestampPatternCps);
						}
					};
					timestampFormatPool.put(timestampPatternCps, tl);
				}
			}
		}
		return tl.get();
	}

	/**
	 * 
	 * 格式化成时间戳格式
	 *
	 * @author warship
	 * @created 2013年11月10日 上午12:27:01
	 *
	 * @param date
	 *                要格式化的日期对象
	 * @return "年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串
	 */
	public static String timestampFormat(Date date) {
		if (date == null) {
			return "";
		}
		return getTimestampFormat().format(date);
	}

	/**
	 * 
	 * 格式化成时间戳格式
	 *
	 * @author warship
	 * @created 2014年1月6日 下午6:16:37
	 *
	 * @param date
	 *                要格式化的日期对象
	 * @return "年年年年月月日日时时分分秒秒"格式的日期字符串
	 */
	public static String simplifyTimestampFormat(Date date) {
		if (date == null) {
			return "";
		}
		return getSimplifyTimestampFormat().format(date);
	}

	/**
	 * 
	 * 格式化成日期格式
	 *
	 * @author warship
	 * @created 2014年3月10日 上午9:33:08
	 *
	 * @param date
	 *                要格式化的日期对象
	 * @return "年年年年月月日日"格式的日期字符串
	 */
	public static String simplifyDateFormat(Date date) {
		if (date == null) {
			return "";
		}
		return getSimplifyDateFormat().format(date);
	}

	/**
	 * 
	 * 格式化成时间戳格式
	 *
	 * @author warship
	 * @created 2013年11月10日 上午12:27:01
	 *
	 * @param date
	 *                要格式化的日期对象
	 * @return "年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串
	 */
	public static String timestampFormatCps(Date date) {
		if (date == null) {
			return "";
		}
		return getTimestampFormatCps().format(date);
	}

	/**
	 * 
	 * 格式化成Unix时间戳格式
	 *
	 * @author warship
	 * @created 2013年12月31日 上午10:18:00
	 *
	 * @param date
	 * @return
	 */
	public static long unixTimestampFormat(Date date) {
		String unixDate = String.valueOf(date.getTime()).substring(0, 10);
		return Long.parseLong(unixDate);
	}

	/**
	 * 
	 * 格式化成时间戳格式
	 *
	 * @author warship
	 * @created 2013年11月10日 上午12:38:36
	 *
	 * @param datetime
	 *                要格式化的日期
	 * @return "年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串
	 */
	public static String timestampFormat(long datetime) {
		return getTimestampFormat().format(new Date(datetime));
	}

	/**
	 * 
	 * 将"年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串转换成Long型日期
	 *
	 * @author warship
	 * @created 2013年11月10日 上午12:43:21
	 *
	 * @param timestampStr
	 *                年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串
	 * @return Long型日期
	 */
	public static long formatTimestampToLong(String timestampStr) {
		try {
			return getTimestampFormat().parse(timestampStr).getTime();
		} catch (ParseException e) {
			return 0L;
		}
	}

	/**
	 * 
	 * 将"年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串转换成日期
	 *
	 * @author warship
	 * @created 2013年12月19日 下午4:55:49
	 *
	 * @param timestampStr
	 *                年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串
	 * @return 日期
	 */
	public static Date formatTimestampToDate(String timestampStr) {
		try {
			return getTimestampFormat().parse(timestampStr);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 
	 * 将"年年年年-月月-日日"格式的日期字符串转换成日期
	 *
	 * @author warship
	 * @created 2013年12月19日 下午4:55:49
	 *
	 * @param timestampStr
	 *                年年年年-月月-日日"格式的日期字符串
	 * @return 日期
	 */
	public static Date formatDateToDate(String dateStr) {
		try {
			return getDateFormat().parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 
	 * 格式化成日期格式
	 *
	 * @author warship
	 * @created 2013年11月10日 上午12:47:56
	 *
	 * @param date
	 *                要格式化的日期
	 * @return "年年年年-月月-日日"格式的日期字符串
	 */
	public static String dateFormat(Date date) {
		if (date == null) {
			return "";
		}
		return getDateFormat().format(date);
	}

	/**
	 * 
	 * 格式化成日期格式
	 *
	 * @author warship
	 * @created 2013年11月25日 上午11:33:46
	 *
	 * @param date
	 *                要格式化的日期
	 * @return "××××年××月××日"格式的日期字符串
	 */
	public static String dateFormatCN(Date date) {
		if (date == null) {
			return "";
		}
		return getDateFormatCN().format(date);
	}

	/**
	 * 
	 * 格式化成日期格式
	 *
	 * @author warship
	 * @created 2013年11月10日 上午12:50:30
	 *
	 * @param datetime
	 *                要格式化的日期
	 * @return "年年年年-月月-日日"格式的日期字符串
	 */
	public static String dateFormat(long datetime) {
		return getDateFormat().format(new Date(datetime));
	}

	/**
	 * 
	 * 将"年年年年-月月-日日"格式的日期字符串转换成Long型日期
	 *
	 * @author warship
	 * @created 2013年11月10日 上午12:52:23
	 *
	 * @param dateStr
	 *                "年年年年-月月-日日"格式的日期字符串
	 * @return Long型日期
	 */
	public static long formatDateToLong(String dateStr) {
		try {
			return getDateFormat().parse(dateStr).getTime();
		} catch (ParseException e) {
			return 0L;
		}
	}

	/**
	 * 
	 * 得到本月的第一天
	 *
	 * @author warship
	 * @created 2013年11月10日 上午12:55:02
	 *
	 * @return 以"年年年年-月月-日日"格式返回当前月第一天的日期
	 */
	public static String getFirstDayOfCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return getDateFormat().format(calendar.getTime());
	}

	/**
	 * 
	 * 得到本月的最后一天
	 *
	 * @author warship
	 * @created 2013年11月10日 上午12:33:42
	 *
	 * @return 以"年年年年-月月-日日"格式返回当前月最后一天的日期
	 */
	public static String getLastDayOfCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return getDateFormat().format(calendar.getTime());
	}

	/**
	 * 
	 * 获取当前日期前（后）的某一天
	 *
	 * @author warship
	 * @created 2013年12月19日 上午11:28:00
	 *
	 * @param offset
	 *                偏移量，即当前日期之前（后）多少天，如果是之前，offset为负的整数
	 * @return 以"年年年年-月月-日日"格式返回要获取的日期
	 */
	public static Date getDayAfterCurrentDate(int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, offset);
		return calendar.getTime();
	}

	/**
	 * 返回以当前时间为基准的七日的结束销售时间
	 * 
	 * @author baiyalu
	 * @created 2014年1月17日 下午3:34:35
	 * 
	 * @return
	 */
	public static Date getSevenDaysAfterOnSale() {
		Calendar calendarAdd = Calendar.getInstance();
		calendarAdd.setTime(new Date());

		calendarAdd.add(Calendar.DAY_OF_MONTH, 6);
		calendarAdd.set(Calendar.HOUR_OF_DAY, 23);
		calendarAdd.set(Calendar.MINUTE, 59);
		calendarAdd.set(Calendar.SECOND, 59);
		return calendarAdd.getTime();
	}

	/**
	 * 根据指定的时间参数获取时间
	 * 
	 * @author noncarget
	 * @created 2014年1月22日 下午3:23:29
	 * 
	 * @return
	 */
	public static Date getTimeByIdentifiedValues(Integer year, Integer month, Integer day, Integer hour,
			Integer minute, Integer second) {
		Calendar calendarAdd = Calendar.getInstance();
		calendarAdd.setTime(new Date());

		calendarAdd.add(Calendar.DAY_OF_MONTH, month);
		calendarAdd.set(Calendar.HOUR_OF_DAY, day);
		calendarAdd.set(Calendar.MINUTE, minute);
		calendarAdd.set(Calendar.SECOND, second);
		return calendarAdd.getTime();
	}

	/**
	 * 将 yyyy-MM-dd 格式的时间转化成 有时分秒的时间戳
	 * 
	 * @param str
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date getTimeChangeHMS(String str, Integer hour, Integer minute, Integer second) {
		try {
			Calendar calendarAdd = Calendar.getInstance();
			calendarAdd.setTime(getDateFormat().parse(str));
			calendarAdd.set(Calendar.HOUR_OF_DAY, hour);
			calendarAdd.set(Calendar.MINUTE, minute);
			calendarAdd.set(Calendar.SECOND, second);
			return calendarAdd.getTime();
		} catch (ParseException e) {
			return null;
		}

	}

	/**
	 * 获取默认日期时间
	 * 
	 * @author sqj
	 * @created 2014-3-31 下午4:10:35
	 *
	 * @return
	 */
	public static Date getDefaultDateTime() {
		return new Date(formatTimestampToDate("1970-01-01 00:00:00").getTime());
	}

	public static boolean checkStrIsDateTime(String dateStr) {
		String eL = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(dateStr);
		boolean dateFlag = m.matches();
		if (!dateFlag) {
			return false;
		}
		return true;
	}

	/**
	 * 获得当天凌晨的时间点
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Date getDayOfFirst() throws ParseException {
		DateFormat formatToStr = new SimpleDateFormat(dateFormatPattern);
		String dateStr = formatToStr.format(new Date());
		DateFormat formatToDate = new SimpleDateFormat(timestampPattern);
		Date dayOfFirst = formatToDate.parse(dateStr + " 00:00:00");
		return dayOfFirst;
	}

	/**
	 * 获得当天最晚的时间点
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static Date getDayOfEnd() throws ParseException {
		DateFormat formatToStr = new SimpleDateFormat(dateFormatPattern);
		String dateStr = formatToStr.format(new Date());
		DateFormat formatToDate = new SimpleDateFormat(timestampPattern);
		Date dayOfEnd = formatToDate.parse(dateStr + " 23:59:59");
		return dayOfEnd;
	}
	// 未完，待续

}