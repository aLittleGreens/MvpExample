package com.klaus.api.http;

import android.text.TextUtils;

import com.google.gson.Gson;

public class JsonUtil {

	private static Gson gson;

	public static <T> T parseJStr2Object(Class<T> clazz, String jstr) {
		if (TextUtils.isEmpty(jstr)) {
			return null;
		}
		if (gson == null) {
			gson = new Gson();
		}
		T bean = null;
		try {
			bean = gson.fromJson(jstr, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	public static String parseObject2Str(Object object) {
		if (object == null) {
			return null;
		}
		if (gson == null) {
			gson = new Gson();
		}
		String str = gson.toJson(object);
		return str;
	}
}
