package com.gx.wda.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gx.wda.bean.SecurityRecordVo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据持久化工具类
 */
public class SPUtils {
    public static final String SP_USER="user";//用户信息
    public static final String SP_USER_ID="userid";//用户ID
    public static final String SP_ACCOUNT="account";//账号
    public static final String SP_PASSWORD="password";//密码
    public static final String SP_REMEMBER="remember";//是否记住密码
    public static final String SP_LANGUAGE="language";//切换语言
    public static final String SP_SECURITY_RECORD="securityRecordList";//安全算法记录
    public static final String SP_VERSION_NEW="version";//是否有新版本
    public static final String SP_VERSION_FIRST ="versionFirst";//是否第一次弹出新版本弹窗



    public static final String SP_HOT_REMEMBER="hotInfo";//账号
    public static final String SP_HOT_NAME="hotSSID";//密码
    public static final String SP_HOT_PASSWORD="hotPassword";//是否记住密码
    public static final String SP_TRIGGER_START="triggerStart";//上次软触发开始时间记录
    public static final String SP_TRIGGER_END="triggerEnd";//上次软触发结束时间记录
    public static final String SP_SOFT_TRIGGER="soft_trigger";//软触发
    public static final String SP_PERMANENT_TRIGGER="permanent_trigger";//永久触发


    public static final String SP_TEMP_TRIGGER_COUNT="temp_trigger_count";//软触发保留条数
    public static final String SP_TEMP_TRIGGER_TIMER="temp_trigger_timer";//软触发记录时间
    public static final String SP_PERMANENT_TRIGGER_COUNT="permanent_trigger_count";//永久触发保留条数
    public static final String SP_PERMANENT_TRIGGER_TIMER="permanent_trigger_timer";//永久触发记录时间
    public static final String SP_PERMANENT_TRIGGER_ROW="permanent_trigger_row";//永久触发记录通道


    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "share_data";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context 上下文
     * @param key     key值
     * @param object  value值
     */
    public static void put(Context context, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context       上下文
     * @param key           key值
     * @param defaultObject 默认value值
     * @return value值
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context 上下文
     * @param key     key值
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     * @param context 上下文
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context 上下文
     * @param key     key值
     * @return 是否存在
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context 上下文
     * @return 所有的键值对
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return Method
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor SharedPreferences.Edito
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }

}