package com.gx.util;

import java.util.List;

/**
 * 开发常用工具类
 * @author 方坚
 *
 */
public class Tools {
	/**
	 * 
	 * @param value
	 * @return 如果字符串不为空或者长度不为零返回true
	 */
	public static boolean isNotNull(String value) {
		if (value == null || "".equals(value.trim()) || "null".equalsIgnoreCase(value)) {
			return false;
		}
		return true;
	}

	/**
	 * ISO编码转换成UTF8编码
	 * @param s
	 * @return
	 */
	public static String ISOtoUTF8(String s) {
		try {
			s = new String(s.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception e) {

		}
		return s;
	}

	/**
	 * 是否为num
	 * @param str
	 * @return boolean
	 */
	public static boolean isNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	/**
	 * 把json字符串转成list实体类
	 * @param stringJson json字符串
	 * @param vo 实体类 
	 * @return
	 */
	public static <T> List<T> jsonToList(String stringJson, Class<T> vo) {
		return (List<T>) com.alibaba.fastjson.JSONArray.parseArray(stringJson, vo);
	}
	
	public static String deleteBlank(String s){
        s = s.trim();//s.trim()方法的作用是去除首尾空格
        if("".equals(s)){//判断删除首尾空格后字符串是否已经为空
            return null;
        }
        StringBuilder ret = new StringBuilder();
        int i = 0;
        for( i = 0;i < s.length() - 1;i++){
            if(s.charAt(i) != ' '){
                ret.append(s.charAt(i));
            }
            if(s.charAt(i) == ' ' && s.charAt(i + 1) != ' '){
                ret.append(s.charAt(i));
            }
        }
        ret.append(s.charAt(i));
        return ret.toString();
    }

}
