package com.klaus.api.http;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class StringHttpResponseHandler implements Callback {

	private static Handler handler;

	public StringHttpResponseHandler() {
		if (handler == null) {
			handler = new Handler(Looper.getMainLooper());
		}
	}

	@Override
	public void onFailure(Call call, IOException e) {
		// 连接失败
		postFail(0);
	}

	private void postFail(final int code) {
		handler.post(new Runnable() {

			@Override
			public void run() {
				onFailed(code);
			}
		});
	}

	@Override
	public void onResponse(Call call, Response response) throws IOException {
		if (response != null) {
			int code = response.code();
			if (code == 200) {
				final String result = response.body().string();
				if (!TextUtils.isEmpty(result)) {
					handler.post(new Runnable() {

						@Override
						public void run() {
							onSuccess(result);
						}
					});
				} else {
					postFail(201);
				}
			} else {
				postFail(code);
			}
		} else {
			postFail(500);
		}
	}

	/**
	 * 成功返回结果
	 * 
	 * @param result
	 */
	public abstract void onSuccess(String result);

	/**
	 * 失败
	 * 
	 * @param status
	 */
	public abstract void onFailed(int status);
}
