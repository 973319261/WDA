package com.gx.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * 获取当前时间_yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getDateTime() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strData = dateFormat.format(date);
		System.out.println(strData);
		return strData;
	}

	/**
	 * 获取当前日期_yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getDate() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strData = dateFormat.format(date);
		System.out.println(strData);
		return strData;
	}

	/**
	 * 获取当前时间_HH:mm
	 * 
	 * @return
	 */
	public static String getTime() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		String strData = dateFormat.format(date);
		System.out.println(strData);
		return strData;
	}

	/**
	 * 获取当天是星期几
	 * 
	 * @return
	 */
	public static String getWeek() {
		String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		Calendar cal = Calendar.getInstance();
		int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		String str = weekDays[week];
		System.out.println(str);
		return str;
	}
	
	/**
	 * 获取当前时间_yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getDateTimeAbb() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String strData = dateFormat.format(date);
		System.out.println(strData);
		return strData;
	}

	/**
	 * 测试
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		getDateTime();
		getDate();
		getTime();
		getWeek();
	}

}