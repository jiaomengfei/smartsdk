package system;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;
import java.util.Map;

/**
 * 跳转工具封装
 *
 * @author dzyssssss
 *
 */
public class IntentUtils {

	/**
	 * 支持int、String,boolean,Serializable数据类型传递
	 *
	 * @param paramContext
	 * @param paramTo
	 * @param paramData
	 */
	public static void switchActivity(Context paramContext, Class paramTo,
									  Map<?, ?> paramData) {

		Intent aIntent = new Intent();
		aIntent.setClass(paramContext, paramTo);

		if (paramData != null) {
			Bundle aBundle = new Bundle();

			for (Object obj : paramData.keySet()) {
				if (obj == null) {
					continue;
				}

				Object key = obj;
				Object value = paramData.get(key);

				if(value.getClass().equals(Long.class)){
					aBundle.putLong((String) key, (Long) value);
				}else if(value.getClass().equals(Integer.class)) {
					aBundle.putInt((String) key, (Integer) value);
				} else if (value.getClass().equals(String.class)) {
					aBundle.putString((String) key, (String) value);
				} else if (value.getClass().equals(Boolean.class)) {
					aBundle.putBoolean((String) key, (Boolean) value);
				}else{
					aBundle.putSerializable((String) key, (Serializable) value);
				}
			}

			aIntent.putExtras(aBundle);
		}

		paramContext.startActivity(aIntent);
	}

	/**
	 * 支持int、String,boolean,Serializable数据类型传递
	 * 
	 * @param paramContext
	 * @param paramTo
	 * @param paramData
	 */
	public static void switchActivityForResult(Activity paramContext,
											   Class paramTo, int paramRequestCode, Map<?, ?> paramData) {
        
	    
		Intent aIntent = new Intent();
		aIntent.setClass(paramContext, paramTo);

		if (paramData != null) {
			Bundle aBundle = new Bundle();

			for (Object obj : paramData.keySet()) {
				if (obj == null) {
					continue;
				}

				Object key = obj;
				Object value = paramData.get(key);

				if(value.getClass().equals(Long.class)){
					aBundle.putLong((String) key, (Long) value);
				}else if(value.getClass().equals(Integer.class)) {
					aBundle.putInt((String) key, (Integer) value);
				} else if (value.getClass().equals(String.class)) {
					aBundle.putString((String) key, (String) value);
				} else if (value.getClass().equals(Boolean.class)) {
					aBundle.putBoolean((String) key, (Boolean) value);
				}else{
					aBundle.putSerializable((String) key, (Serializable) value);
				}
			}

			aIntent.putExtras(aBundle);
		}
		paramContext.startActivityForResult(aIntent, paramRequestCode);
	}
}
