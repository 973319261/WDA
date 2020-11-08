package com.gx.wda.util;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gx.wda.bean.SecurityRecordVo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 安全算法记录
 */
public class SecurityRecordUtil {
    private static Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private static SecurityRecordVo securityRecordVo;
    static {//初始化空数据
        securityRecordVo=new SecurityRecordVo();
        securityRecordVo.setTypeId(0);//空数据
    }
    /**
     * 获取本地安全算法记录
     * @param activity
     * @return
     */
    public static List<SecurityRecordVo> getSecurityRecord(Activity activity){
        String securityRecordListStr= (String) SPUtils.get(activity,SPUtils.SP_SECURITY_RECORD,"");
        List<SecurityRecordVo> securityRecordList=null;
        if (!"".equals(securityRecordListStr)){//不为空
            Type type=new TypeToken<List<SecurityRecordVo>>(){}.getType();//转换类型
            securityRecordList=gson.fromJson(securityRecordListStr,type);//获取本地的安全算法历史记录
        }else {//初始化10条空数据
            securityRecordList=new ArrayList<>();
            for (int i=0;i<10;i++){
                securityRecordList.add(securityRecordVo);
            }
            SPUtils.put(activity,SPUtils.SP_SECURITY_RECORD,gson.toJson(securityRecordList));//保存记录记录到本地
        }
        return securityRecordList;
    }

    /**
     * 保存（设置）安全算法记录
     * @param activity
     * @param securityRecordVo
     */
    public static void setSecurityRecord(Activity activity,SecurityRecordVo securityRecordVo){
        String securityRecordListStr= (String) SPUtils.get(activity,SPUtils.SP_SECURITY_RECORD,"");
        List<SecurityRecordVo> securityRecordList;//安全算法记录列表
        if (!"".equals(securityRecordListStr)){//不为空
            Type type=new TypeToken<List<SecurityRecordVo>>(){}.getType();//转换类型
            securityRecordList=gson.fromJson(securityRecordListStr,type);//获取本地的安全算法历史记录
        }else {
            securityRecordList=new ArrayList<>();//初始化
        }
        if (securityRecordList.size()>=10){//只保存10条历史记录
            securityRecordList.remove(0);//移除一条数据
        }
        if (!securityRecordList.contains(securityRecordVo)){//
            securityRecordList.add(securityRecordVo);//添加历史记录
            SPUtils.put(activity,SPUtils.SP_SECURITY_RECORD,gson.toJson(securityRecordList));//保存记录记录到本地
        }
    }

    /**
     * 通过索引删除安全算法记录
     * @param activity
     * @param position
     */
    public static void deleteSecurityRecord(Activity activity,int position){
        String securityRecordListStr= (String) SPUtils.get(activity,SPUtils.SP_SECURITY_RECORD,"");
        List<SecurityRecordVo> securityRecordList;//安全算法记录列表
        if (!"".equals(securityRecordListStr)){//不为空
            Type type=new TypeToken<List<SecurityRecordVo>>(){}.getType();//转换类型
            securityRecordList=gson.fromJson(securityRecordListStr,type);//获取本地的安全算法历史记录
            Collections.reverse(securityRecordList);//倒序
            securityRecordList.remove(position);//移除
            securityRecordList.add(securityRecordVo);//添加一条空数据
            Collections.reverse(securityRecordList);//还原
            SPUtils.put(activity,SPUtils.SP_SECURITY_RECORD,gson.toJson(securityRecordList));//保存记录记录到本地
        }
    }
}
