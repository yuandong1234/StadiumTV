package com.kasai.stadium.tv.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * sharePreferences工具类
 */
public class BaseSharePreferences {

    private SharedPreferences share;
    private SharedPreferences.Editor editor;

    public BaseSharePreferences(Context context, String name) {
        share = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = share.edit();
    }


    /**
     * 保存数据的方法
     *
     * @param key
     * @param object
     */
    public void putValue(String key, Object object) {
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
            editor.putString(key, (String) object);
        }
        editor.commit();
        //editor.apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return share.getBoolean(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        return share.getFloat(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return share.getInt(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return share.getLong(key, defaultValue);
    }

    public String getString(String key, String defaultValue) {
        return share.getString(key, defaultValue);
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清空所有数据
     */
    public void clear() {
        editor.clear();
        editor.commit();
    }
}
