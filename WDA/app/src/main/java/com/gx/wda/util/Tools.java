package com.gx.wda.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 开发常用工具类
 * @author 方坚
 *
 */
public class Tools {
	/**
	 * 
	 * @param value 字符串
	 * @return 如果字符串不为空或者长度不为零返回true
	 */
	public static boolean isNotNull( String value ) {
		if( value == null || "".equals( value.trim()) || "null".equalsIgnoreCase(value) ) {
			return false;
		}
		return true;
	}
	public static String toString (String value){
		return value==null?"":value.trim();
	}
	/**
	 * ISO编码转换成UTF8编码
	 * @param s 字符串
	 * @return UTF编码字符串
	 */
	public static String ISOtoUTF8(String s) { 
		try { 
			s = new String(s.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception ignored) {
			
		} 
		return s; 
	}
	
	/**
	 * 是否为num
	 * @param str 字符串
	 * @return boolean
	 */
	public static boolean isNum(String str){	
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");	
	}

	/**
	 * 判断车牌号
	 * @param str
	 * @return
	 */
	public static boolean isCarNum(String str){
		Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5|WJ]{1}[A-Z0-9]{6}$");
		Matcher matcher = pattern.matcher(str);
		if (!matcher.matches()) {
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 金额小数点后面和前面字体大小不一致的实现
	 * //调用
	 * 	SpannableString spannableString = changTVsize("53.9");
	 *         chooseMoviePrice.setText(spannableString);
	 * @param value
	 * @return
	 */
	public static SpannableString setMoneySize(String value) {
		SpannableString spannableString = new SpannableString(value);
		if (value.contains(".")) {
			spannableString.setSpan(new RelativeSizeSpan(0.8f), value.indexOf("."), value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		if (value.startsWith("￥")){
			spannableString.setSpan(new RelativeSizeSpan(0.8f), value.indexOf("￥"), 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return spannableString;
	}

	/**
	 * 把金额的￥替换掉
	 * @param value
	 * @return
	 */
	public static Double getMoney(String value) {
		if (value.startsWith("￥")){
			value=	value.replace("￥","");
		}
		return Double.parseDouble(value);
	}

	/*  <br>　　　　　2019年1月16日已知
   中国电信号段
       133,149,153,173,174,177,180,181,189,199
   中国联通号段
       130,131,132,145,146,155,156,166,175,176,185,186
   中国移动号段
       134(0-8),135,136,137,138,139,147,148,150,151,152,157,158,159,165,178,182,183,184,187,188,198
   上网卡专属号段（用于上网和收发短信，不能打电话）
       如中国联通的是145
   虚拟运营商
       电信：1700,1701,1702
       移动：1703,1705,1706
       联通：1704,1707,1708,1709,171
   卫星通信： 1349 <br>　　　　　未知号段：141、142、143、144、154
   */

	/**
	 * 判断是不是手机号
	 * @param str 字符串
	 * @return boolean
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		String s2="^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";// 验证手机号
		if(isNotNull(str)){
			p = Pattern.compile(s2);
			m = p.matcher(str);
			b = m.matches();
		}
		return b;
	}
	/**
	 *验证身份证号码
	 */
	public static boolean isIdCard(String id){
		String str = "[1-9]{2}[0-9]{4}(19|20)[0-9]{2}"
				+ "((0[1-9]{1})|(1[1-2]{1}))((0[1-9]{1})|([1-2]{1}[0-9]{1}|(3[0-1]{1})))"
				+ "[0-9]{3}[0-9x]{1}";
		Pattern pattern = Pattern.compile(str);
		return pattern.matcher(id).matches() ? true : false;
	}

	/**
	 * 从身份证截取生日
	 * @param idCard
	 * @return
	 */
	public static String getBirthday(String idCard) {
		String birthday="";
		Calendar c=Calendar.getInstance();
		switch(idCard.length()) {
			case 18:
				String year = idCard.substring(6, 10);
				String month = idCard.substring(10,12);
				String day = idCard.substring(12,14);
				birthday=year+"-"+ month+"-"+ day;
				break;
			default:
				break;
		}
		return birthday;
	}
	public static <T> List<T> getObjectList(String jsonString, Class<T> cls){
		List<T> list = new ArrayList<T>();
		try {
			Gson gson = new Gson();
			JsonArray arry = new JsonParser().parse(jsonString).getAsJsonArray();
			for (JsonElement jsonElement : arry) {
				list.add(gson.fromJson(jsonElement, cls));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	// 两次点击间隔不能少于1000ms
	private static final int FAST_CLICK_DELAY_TIME = 1000;
	private static long lastClickTime;

	public static boolean isFastClick() {
		boolean flag = true;
		long currentClickTime = System.currentTimeMillis();
		if ((currentClickTime - lastClickTime) >= FAST_CLICK_DELAY_TIME ) {
			flag = false;
		}
		lastClickTime = currentClickTime;
		return flag;
	}

	//时间计数器，最多只能到99小时，如需要更大小时数需要改改方法
	public static String showTimeCount(long time) {
		System.out.println("time="+time);
		if(time >= 360000000){
			return "00:00:00";
		}
		String timeCount = "";
		long hourc = time/3600000;
		String hour = "0" + hourc;
		System.out.println("hour="+hour);
		hour = hour.substring(hour.length()-2, hour.length());
		System.out.println("hour2="+hour);

		long minuec = (time-hourc*3600000)/(60000);
		String minue = "0" + minuec;
		System.out.println("minue="+minue);
		minue = minue.substring(minue.length()-2, minue.length());
		System.out.println("minue2="+minue);

		long secc = (time-hourc*3600000-minuec*60000)/1000;
		String sec = "0" + secc;
		System.out.println("sec="+sec);
		sec = sec.substring(sec.length()-2, sec.length());
		System.out.println("sec2="+sec);
		timeCount = hour + ":" + minue + ":" + sec;
		System.out.println("timeCount="+timeCount);
		return timeCount;
	}


	/**
	 * 判断是否为8位的16进制
	 * @param str
	 * @return
	 */
	public static boolean isHexadecimal(String str){
		boolean flag=false;
		String regex="^[A-Fa-f0-9]{4}|[A-Fa-f0-9]{8}$";
		if (!regex.matches(str)){
			flag=true;
		}
		return flag;
	}
	/**
	 * 获取当前apk版本号信息
	 * @param ctx
	 * @return
	 */
	public static int getVersionCode(Context ctx) {
		int version = 0;
		try {
			PackageManager packageManager = ctx.getPackageManager();
			//getPackageName()是你当前程序的包名
			PackageInfo packInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
			version = packInfo.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return version;
	}
	/**
	 * 获取当前apk版本号信息
	 * @param ctx
	 * @return
	 */
	public static String getVersionName(Context ctx) {
		String version = "";
		try {
			PackageManager packageManager = ctx.getPackageManager();
			//getPackageName()是你当前程序的包名
			PackageInfo packInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
			version = packInfo.versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return version;
	}
}
