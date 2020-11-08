package com.gx.wda.util;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EncodingUtil {


    /**
     * 截取byte数组   不改变原数组
     * @param b 原数组
     * @param off 偏差值（索引）
     * @param length 长度
     * @return 截取后的数组
     */
    public static byte[] subByte(byte[] b,int off,int length){
        byte[] b1 = new byte[length];
        System.arraycopy(b, off, b1, 0, length);
        return b1;
    }


    /**
     * hex转byte数组
     * @param hex
     * @return
     */
    public static byte[] hexToByte(String hex){
        int m = 0, n = 0;
        int byteLen = hex.length() / 2; // 每两个字符描述一个字节
        byte[] ret = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            int intVal = Integer.decode("0x" + hex.substring(i * 2, m) + hex.substring(m, n));
            ret[i] = Byte.valueOf((byte)intVal);
        }
        return ret;
    }

    private static final char[] HEX_CHAR_TABLE = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    //byte转16进制字符串
    public static String toHexString(byte[] array) {
        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(HEX_CHAR_TABLE[(b & 0xf0) >> 4]);
            sb.append(HEX_CHAR_TABLE[b & 0x0f]);
        }
        return sb.toString();
    }

    /**
     * 字节转十六进制
     * @param b 需要进行转换的byte字节
     * @return 转换后的Hex字符串
     */
    public static String byteToHex(byte b){
        String hex = Integer.toHexString(b & 0xFF);
        if(hex.length() < 2){
            hex = "0" + hex;
        }
        return hex;
    }


    /**
     * 字符串转换为16进制字符串
     *
     * @param s
     * @return
     */
    public static String stringToHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }


    /**
     * 16进制字符串转换为字符串
     *
     * @param s
     * @return
     */
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(
                        s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "gbk");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }


    /**
     * 16进制字符串转换为IP
     *
     * @param s
     * @return
     */
    public static String hexStringToIP(String s) {
        String ip = "";
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        String[] baKeyword = new String[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (0xff & Integer.parseInt(
                        s.substring(i * 2, i * 2 + 2), 16))+".";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < baKeyword.length; i++) {
            ip+=baKeyword[i];
        }
        return ip;
    }


    /**
     * 字符串不足位补0
     * @param str
     * @param strLength
     * @return
     */
    public static String addZeroForNum(String str, int strLength,boolean isLeft) {
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
            if(isLeft){
                sb.append("0").append(str);// 左补0
            }else{
                sb.append(str).append("0");//右补0
            }
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }


    /**
     * 大端转小端
     * @param s
     * @return
     */

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String sToB(String[] s){
        List<String> list = new ArrayList<String>();
        String a = "";
        for(int i=1;i<s.length;i++){
            a+=s[i];
            if(i%2==0){
                list.add(a);
                a="";
            }
        }
        Collections.reverse(list);
        return list.stream().collect(Collectors.joining(""));
    }


    /**
     * CRC8校验位计算
     * @param buffer
     * @return
     */
    public static String CRC8(byte[] buffer)
    {
        int crci=0x00;   //起始字节FF
        for (int j = 0; j < buffer.length; j++)
        {
            crci ^= buffer[j] & 0xFF;
            for (int i = 0; i < 8; i++)
            {
                if ((crci & 0x80) != 0)
                {
                    crci <<= 1;
                    crci ^= 0x07;    //多项式当中的那个啥的，不同多项式不一样
                }
                else
                {
                    crci <<= 1;
                }
            }
        }
        crci=crci ^0x55;
        return byteToHex((byte)crci);
    }


    /**
     * XOR-BCC校验
     * @param data
     * @return
     */
    public static String getBCC(byte[] data) {

        String ret = "";
        byte BCC[]= new byte[1];
        for(int i=0;i<data.length;i++)
        {
            BCC[0]=(byte) (BCC[0] ^ data[i]);
        }
        String hex = Integer.toHexString(BCC[0] & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        ret += hex.toUpperCase();
        return ret;
    }


    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     * @param
     * @return
     */
    public static String timeStamp2Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss:SSS";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)));
    }


    /**
     * 根据日期获取 星期 （2019-05-06 ——> 星期一）
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat"};
        Calendar cal = Calendar.getInstance();
        Date date;
        try {
            date = f.parse(datetime);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //一周的第几天
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 将16进制转换为二进制字符串
     *
     * @param hexString
     * @return
     */
    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0)
            return null;
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    /**
     * @Description:	二进制转换成十进制
     * @param binarySource
     * @return int
     */
    public static String binaryToDecimal(String binarySource) {
        BigInteger bi = new BigInteger(binarySource, 2);	//转换为BigInteger类型
        return bi.toString();		//转换成十进制
    }


    /**
     * 每两个字中间加一个空格
     * @param str
     * @return
     */
    public static String addSpace(String str){
        String ret = "";
        String[] chars = str.split("");
        for(int i=0;i<chars.length;i++){
            ret+=chars[i];
            if(i%2==0){
                ret+=" ";
            }
        }
        return ret;
    }


}
