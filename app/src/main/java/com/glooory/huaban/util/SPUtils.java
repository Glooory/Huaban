package com.glooory.huaban.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * Created by Glooory on 2016/8/27 0027.
 */
public class SPUtils {

    public SPUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 保存在手机里面的文件名和读写模式
     */
    public static final String SP_FILE_NAME = "shared_data";
    public static final int SP_RW_MODE = Context.MODE_PRIVATE;

    /**
     * 异步提交方法
     */
    public static void putByApply(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME, SP_RW_MODE);

        SharedPreferences.Editor editor = sp.edit();
        judgeDataTypeToPut(editor, key, object);
        editor.apply();
    }

    /**
     * 同步提交方法
     *
     * @param context
     * @param key
     * @param object
     * @return
     */
    public static boolean putByCommit(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME, SP_RW_MODE);
        SharedPreferences.Editor editor = sp.edit();
        judgeDataTypeToPut(editor, key, object);
        return editor.commit();
    }

    /**
     * 根据不同的类型 使用不同的写入方法
     *
     * @param editor
     * @param key
     * @param object
     */
    private static void judgeDataTypeToPut(SharedPreferences.Editor editor,
                                           String key, Object object) {
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
        } else if (object instanceof Set) {
            editor.putStringSet(key, (Set<String>) object);
        } else {
            editor.putString(key, object.toString());
        }
    }

    public static void putAdd(SharedPreferences.Editor editor, String key, Object object) {
        judgeDataTypeToPut(editor, key, object);
    }

    /**
     * 取值方法
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME, SP_RW_MODE);

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
        } else if (defaultObject instanceof Set) {
            return sp.getStringSet(key, (Set<String>) defaultObject);
        }

        return null;
    }

    /**
     * 移除某个key对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME, SP_RW_MODE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME, SP_RW_MODE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 判断某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME, SP_RW_MODE);
        return sp.contains(key);
    }

    /**
     * 获取所有的键值对
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME, SP_RW_MODE);
        return sp.getAll();
    }
}
