package com.yanger.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description 对日期操作的公共方法类
 * @author 杨号
 * @date 2018年10月13日
 */
public class DateUtils {

	/**
	 * @description 格式化日期的方法
	 * @author 杨号
	 * @date 2018年10月13日-下午4:27:03
	 * @param date：带格式化的日期
	 * @param pattern：格式化的表达式，比如
	 *            yyyy-MM-dd
	 * @return 格式化后的字符串
	 */
	public static String formatDate(Date date, String pattern) {
		// 建立日期FORMAT的实例
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	/**
	 * @description 格式化日期的方法
	 * @author 杨号
	 * @date 2018年10月13日-下午4:27:49
	 * @param date：带格式化的日期，默认为yyyy-MM-dd
	 * @return 格式化后的字符串
	 */
	public static String formatDate(Date date) {
		return formatDate(date, "yyyy-MM-dd");
	}

	/**
	 * @description 获取N天以后的日期对象
	 * @author 杨号
	 * @date 2018年10月13日-下午4:42:58
	 * @param date：传入的日期对象
	 * @param n：往后顺延的天数
	 * @return 顺延后的日期对象
	 */
	public static Date getPreviousNDate(Date date, long n) {
		long time = date.getTime();
		// 用毫秒数来计算新的日期
		time = time - n * 24 * 60 * 60 * 1000;
		return new Date(time);
	}

	/**
	 * @description 获取N天以前的日期对象
	 * @author 杨号
	 * @date 2018年10月13日-下午5:03:07
	 * @param date：传入的日期对象
	 * @param n：往后顺延的天数
	 * @return 往前推算后的日期对象
	 */
	public static Date getNextNDate(Date date, long n) {
		long time = date.getTime();
		// 用毫秒数来计算新的日期
		time = time + n * 24 * 60 * 60 * 1000;
		return new Date(time);
	}

	/**
	 * @description 计算传入日期的前一天
	 * @author 杨号
	 * @date 2018年10月13日-下午5:04:05
	 * @param date：传入日期
	 * @return 传入日期的前一天的日期对象
	 */
	public static Date getPreviousDate(Date date) {
		return getPreviousNDate(date, 1);
	}

	/**
	 * @description 计算传入日期的后一天
	 * @author 杨号
	 * @date 2018年10月13日-下午5:04:33
	 * @param date：传入日期
	 * @return 传入日期的后一天的日期对象
	 */
	public static Date getNextDate(Date date) {
		return getNextNDate(date, 1);
	}

	/**
	 * @description 根据起始日期、终止日期计算天数
	 * @author 杨号
	 * @date 2018年10月13日-下午5:04:57
	 * @param startDate：起始日期
	 * @param endDate：终止日期
	 * @return 天数区间
	 */
	public static int getDaysCount(Date startDate, Date endDate) {
		return getDaysCount(startDate, 0, endDate, 0);
	}

	/**
	 * @description 根据年数返回所包含的天数
	 * @author 杨号
	 * @date 2018年10月13日-下午5:05:24
	 * @param year：年数
	 * @return 该年所包含的天数
	 */
	public static int getDaysFromYear(int year) {
		// 判断是平年还是闰年
		if ((year % 400 == 0) || (year % 100 != 0) && (year % 4 == 0)) {
			return 366;
		} else
			return 365;
	}

	/**
	 * @description 根据起始日期、起始时间、终止日期、终止时间计算天数
	 * @author 杨号
	 * @date 2018年10月13日-下午5:08:22
	 * @param startDate：起始日期
	 * @param startHour：起始小时
	 * @param endDate：终止日期
	 * @param endHour：终止小时
	 * @return
	 */
	public static int getDaysCount(Date startDate, int startHour, Date endDate, int endHour) {
		// 根据起始日期计算起始的毫秒
		long startTime = startDate.getTime();
		// 根据终止日期计算终止的毫秒
		long endTime = endDate.getTime();
		// 通过起始毫秒和终止毫秒的差值，计算天数
		int dayCount = (int) ((endTime - startTime) / (24 * 60 * 60 * 1000) + 1);
		if (endHour <= startHour) {
			if (startHour == 24 && endHour == 0) {
				dayCount = dayCount - 2;
			} else {
				dayCount = dayCount - 1;
			}
		}
		return dayCount;

	}

	/**
	 * @description 比较两个日期的大小
	 * @author 杨号
	 * @date 2018年10月13日-下午5:08:52
	 * @param startDate：开始日期
	 * @param endDate：结束日期
	 * @return 开始日期小于结束日期 0 ： 开始日期等于结束日期 1 ： 开始日期大于结束日期
	 */
	public static int compareDate(Date startDate, Date endDate) {
		long startTime = startDate.getTime();
		long endTime = endDate.getTime();
		if (startTime < endTime) {
			return -1;
		}
		if (startTime == endTime) {
			return 0;
		}
		return 1;
	}

}
