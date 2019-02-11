package com.klaus.api.http;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class ObjectHttpResponseHandler<T> implements Callback {
    private String TAG = "ObjectHttpResponseHandler";

    private Class<T> clazz;
    private static Handler handler;

    public ObjectHttpResponseHandler(Class<T> clazz){
        this.clazz = clazz;
        if(handler == null){
            handler = new Handler(Looper.getMainLooper());
        }
    }

    @Override
    public void onFailure(Call arg0, IOException arg1){
        postFail(0);
    }

    private void postFail(final int code){
        handler.post(new Runnable(){

            @Override
            public void run(){
                Log.d(TAG,"postFail --->"+code);
                onFailed(code);
            }
        });
    }

    @Override
    public void onResponse(Call arg0, Response response) throws IOException {
        if(response != null){
            Log.d(TAG,"onResponse result ---> " + response.toString());
            int code = response.code();
            if(code == 200){
                String result =response.body().string();
                if(!TextUtils.isEmpty(result)){
                    Log.d(TAG,"onResponse result ---> " + result);
                    try{
                        final T t = JsonUtil.parseJStr2Object(clazz,result);
                        if(null != t){
                            handler.post(new Runnable(){
                                @Override
                                public void run(){
                                    onSuccess(t);
                                }
                            });
                        }else{
                            // 对象为null
                            postFail(4);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        // 异常
                        postFail(3);
                    }
                }else{
                    // 返回的结果字符串是null或空字符
                    postFail(2);
                }
            }else{
                postFail(code);
            }
        }else{
            postFail(1);
        }
    }

    /**
     * 成功返回结果
     *
     * @param result
     */
    public abstract void onSuccess(T result);

    /**
     * 失败
     *
     * @param status
     */
    public abstract void onFailed(int status);
}
