package com.klaus.api.http;

import java.util.HashMap;
import java.util.Map;

/**
 * URL 请求参数
 * 
 * @author bruce.chen
 * 
 *         2016年1月26日
 */
public class RequestUrlParams {

	protected final Map<String, String> urlParams = new HashMap<String, String>();

	/**
	 * Constructs a new empty {@code RequestParams} instance.
	 */
	public RequestUrlParams() {}

	public RequestUrlParams(String key, String value) {
		put(key, value);
	}

	public RequestUrlParams(String key, int value) {
		put(key, String.valueOf(value));
	}

	public RequestUrlParams(String key, long value) {
		put(key, String.valueOf(value));
	}

	public RequestUrlParams(String key, float value) {
		put(key, String.valueOf(value));
	}

	public RequestUrlParams(String key, Boolean value) {
		put(key, String.valueOf(value));
	}

	/**
	 * Constructs a new RequestParams instance containing the key/value string
	 * params from the specified map.
	 *
	 * @param source
	 *            the source key/value string map to add.
	 */
	public RequestUrlParams(Map<String, String> source) {
		if (source != null) {
			for (Map.Entry<String, String> entry : source.entrySet()) {
				put(entry.getKey(), entry.getValue());
			}
		}
	}

	public void put(String key, String value) {
		if (key != null && value != null) {
			urlParams.put(key, value);
		}
	}

	public void put(String key, int value) {
		put(key, String.valueOf(value));
	}

	public void put(String key, long value) {
		put(key, String.valueOf(value));
	}

	public void put(String key, float value) {
		put(key, String.valueOf(value));
	}

	public void put(String key, boolean value) {
		put(key, String.valueOf(value));
	}

	public Map<String, String> getUrlParams() {
		return urlParams;
	}

	public boolean isNotEmpty() {
		return urlParams.size() > 0;
	}
}
