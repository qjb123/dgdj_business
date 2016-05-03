package net.ezcx.dongguandaojia.business.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 配置文件 存值全都是String
 * 
 */
public class PreferenceUtil {
	/**
	 * 存值全都是String
	 * 
	 * @param key
	 * @param value
	 */
	public static void setEdit(String key, String value,Context context) {
		SharedPreferences sp = context.getSharedPreferences("DGDJ",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 获取保存的值
	 * @param key
	 * @param context
	 * @return
	 */
	public static String getValue(String key,Context context) {
		SharedPreferences sp = context.getSharedPreferences("DGDJ",
				Context.MODE_PRIVATE);
		return sp.getString(key, null);
	}
	
	/**
	 * 清除保存的值
	 * 
	 */
	public static void clearValue(String key, Context context) {
		SharedPreferences sp = context.getSharedPreferences("DGDJ",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.remove(key);
		editor.commit();
	}
	

}
