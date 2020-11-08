package com.gx.wda.util;

/**
 * 服务器访问路径工具类
 */
public class ServiceUrls {

    public static String responseText="无法连接网络，请稍后再试";
    public static String serviceUrl="http://39.108.0.146:8080/WDAService/";
    //public static String serviceUrl="http://192.168.137.1:8080/WDAService/";
   // public static String serviceUrl="http://192.168.22.1:8080/WDAService/";
    public static String rtmpUrl="rtmp://219.129.216.114:1935/live/";
    //Android虚拟机 AVD访问电脑的地址
    //private static String serviceUrl="http://10.0.2.2:8080/WDAService/";
    private static String urlPostfix=".do";
    //VCI
    //IP
    public static String VCI_IPADDRESS = "192.168.43.2";
    //端口
    public static int VCI_PORT = 8400;
    /**
     * 公共模块
     * @param method 方法
     * @return url
     */
    public static String getCommonMethodUrl(String method){
        return serviceUrl+"app/common/"+method+urlPostfix;
    }
    /**
     * 用户模块
     * @param method 方法
     * @return url
     */
    public static String getUserMethodUrl(String method){
        return serviceUrl+"app/user/"+method+urlPostfix;
    }
    /**
     * 安全算法模块
     * @param method 方法
     * @return url
     */
    public static String getSecurityMethodUrl(String method){
        return serviceUrl+"app/security/"+method+urlPostfix;
    }
    /**
     * DTC模块
     * @param method 方法
     * @return url
     */
    public static String getDtcMethodUrl(String method){
        return serviceUrl+"app/dtc/"+method+urlPostfix;
    }
    /**
     * 分享查询模块
     * @param method
     * @return
     */
    public static String getShareMethodUrl(String method){
        return serviceUrl+"app/share/"+method+urlPostfix;
    }
    /**
     * 版本更新
     * @param method
     * @return
     */
    public static String getVersionMethodUrl(String method){
        return serviceUrl+"app/version/"+method+urlPostfix;
    }
}
