package com.klaus.api.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author
 */
public class HttpHelper {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static Context mContext;
    private static HttpHelper helper;
    public String DOMAIN;
    /**
     * 默认请求超时时间35s
     */
    protected static int httpConnectTimeOut = 35 * 1000;
    // OK HTTP
    private OkHttpClient okHttpClient;

    private HttpHelper(Context context, String domain) {
        mContext = context;
        DOMAIN = domain;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(new SimpleCookieJar());
        builder.connectTimeout(httpConnectTimeOut, TimeUnit.MILLISECONDS);
        okHttpClient = builder.build();
    }

    public static void init(Context context, String domain) {
        if (helper == null) {
            synchronized (HttpHelper.class) {
                if (helper == null)
                    helper = new HttpHelper(context, domain);
            }
        }
    }

    public static HttpHelper getHelper() {
        if (helper == null) {
            throw new RuntimeException("init helper");
        }
        return helper;
    }

    public static void reset() {
        helper = null;
    }

    public OkHttpClient getClient() {
        return okHttpClient;
    }

    /**
     * post请求
     *
     * @param url             访问URL
     * @param hashMap         请求头
     * @param requestBody     body参数
     * @param responseHandler 响应监听
     */
    public <T> void post(String url, HashMap<String, String> hashMap, RequestBody requestBody, ObjectHttpResponseHandler<T> responseHandler) {
        // 请求头
        Headers headers = Headers.of(hashMap);
        // 创建一个请求
        Request request = new Request.Builder().url(DOMAIN + url).headers(headers).post(requestBody).build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(responseHandler);
    }

    /**
     * post请求
     *
     * @param url             访问URL
     * @param hashMap         请求头
     * @param requestBody     body参数
     * @param params          url参数
     * @param responseHandler 响应监听
     */
    public <T> void post(String url, HashMap<String, String> hashMap, RequestBody requestBody, RequestUrlParams params, ObjectHttpResponseHandler<T> responseHandler) {
        if (params != null && params.isNotEmpty()) {
            url = getUrl(new StringBuffer(url), params);
        }
        // 请求头
        Headers headers = Headers.of(hashMap);
        // 创建一个请求
        Request request = new Request.Builder().url(DOMAIN + url).headers(headers).post(requestBody).build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(responseHandler);
    }

    /**
     * post请求
     *
     * @param url             访问URL
     * @param requestBody     body参数
     * @param responseHandler 响应监听
     */
    public <T> void post(String url, RequestBody requestBody, ObjectHttpResponseHandler<T> responseHandler) {
        // 创建一个请求
        Request request = new Request.Builder().url(DOMAIN + url).post(requestBody).build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(responseHandler);
    }

    /**
     * post请求
     *
     * @param url             访问URL
     * @param requestBody     body参数
     * @param params          url参数
     * @param responseHandler 响应监听
     */
    public <T> void post(String url, RequestBody requestBody, RequestUrlParams params, ObjectHttpResponseHandler<T> responseHandler) {
        if (params != null && params.isNotEmpty()) {
            url = getUrl(new StringBuffer(url), params);
        }
        // 创建一个请求
        Request request = new Request.Builder().url(DOMAIN + url).post(requestBody).build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(responseHandler);
    }

    /**
     * post请求
     *
     * @param url             URL
     * @param hashMap         请求头
     * @param object          对象,解析成字符串
     * @param responseHandler 回调
     */
    public <T> void post(String url, HashMap<String, String> hashMap, Object object, ObjectHttpResponseHandler<T> responseHandler) {
        RequestBody body = null;
        if (object != null) {
            String json = JsonUtil.parseObject2Str(object);
            // body 参数
            body = RequestBody.create(JSON, json);
        }
        // 请求头
        Headers headers = Headers.of(hashMap);
        // 创建一个请求
        Request request = new Request.Builder().url(DOMAIN + url).headers(headers).post(body).build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(responseHandler);
    }

    /**
     * post请求
     *
     * @param url             URL
     * @param hashMap         请求头
     * @param params          url参数
     * @param object          对象,解析成字符串
     * @param responseHandler 回调
     */
    public <T> void post(String url, HashMap<String, String> hashMap, RequestUrlParams params, Object object, ObjectHttpResponseHandler<T> responseHandler) {
        if (params != null && params.isNotEmpty()) {
            url = getUrl(new StringBuffer(url), params);
        }
        String json = JsonUtil.parseObject2Str(object);
        // body 参数
        RequestBody body = RequestBody.create(JSON, json);
        // 请求头
        Headers headers = Headers.of(hashMap);
        // 创建一个请求
        Request request = new Request.Builder().url(DOMAIN + url).headers(headers).post(body).build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(responseHandler);
    }

    /**
     * post请求
     *
     * @param url             URL
     * @param object          对象,解析成字符串
     * @param responseHandler 回调
     */
    public <T> void post(String url, Object object, ObjectHttpResponseHandler<T> responseHandler) {
        String json = JsonUtil.parseObject2Str(object);
        // body 参数
        RequestBody body = RequestBody.create(JSON, json);
        // 创建一个请求
        Request request = new Request.Builder().url(DOMAIN + url).post(body).build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(responseHandler);
    }


    /**
     * post请求
     *
     * @param url             URL
     * @param params          url参数
     * @param object          对象,解析成字符串
     * @param responseHandler 回调
     */
    public <T> void post(String url, RequestUrlParams params, Object object, ObjectHttpResponseHandler<T> responseHandler) {
        if (params != null && params.isNotEmpty()) {
            url = getUrl(new StringBuffer(url), params);
        }
        String json = JsonUtil.parseObject2Str(object);
        // body 参数
        RequestBody body = RequestBody.create(JSON, json);
        // 创建一个请求
        Request request = new Request.Builder().url(DOMAIN + url).post(body).build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(responseHandler);
    }

    /**
     * post请求
     *
     * @param url             URL
     * @param params          url参数
     * @param body            body参数
     * @param responseHandler 回调
     */
    public <T> void post(String url, RequestUrlParams params, RequestBody body, ObjectHttpResponseHandler<T> responseHandler) {
        if (params != null && params.isNotEmpty()) {
            url = getUrl(new StringBuffer(url), params);
        }
        // 创建一个请求
        Request request = new Request.Builder().url(DOMAIN + url).post(body).build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(responseHandler);
    }

    /**
     * * put请求
     *
     * @param url             访问URL
     * @param hashMap         请求头
     * @param requestBody     body参数
     * @param responseHandler 回调
     */
    public <T> void put(String url, HashMap<String, String> hashMap, RequestBody requestBody, ObjectHttpResponseHandler<T> responseHandler) {
        // 请求头
        Headers headers = Headers.of(hashMap);
        // 创建一个请求
        Request request = new Request.Builder().url(DOMAIN + url).headers(headers).put(requestBody).build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(responseHandler);
    }

    /**
     * * put请求
     *
     * @param url             访问URL
     * @param requestBody     body参数
     * @param responseHandler 回调
     */
    public <T> void put(String url, RequestBody requestBody, ObjectHttpResponseHandler<T> responseHandler) {
        // 创建一个请求
        Request request = new Request.Builder().url(DOMAIN + url).put(requestBody).build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(responseHandler);
    }

    /**
     * put 请求
     *
     * @param url             URL
     * @param hashMap         请求头
     * @param object          对象,转成json
     * @param responseHandler 回调
     */
    public <T> void put(String url, HashMap<String, String> hashMap, Object object, ObjectHttpResponseHandler<T> responseHandler) {
        String json = JsonUtil.parseObject2Str(object);
        // body 参数
        RequestBody body = RequestBody.create(JSON, json);
        // 请求头
        Headers headers = Headers.of(hashMap);
        // 创建一个请求
        Request request = new Request.Builder().url(DOMAIN + url).headers(headers).put(body).build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(responseHandler);
    }

    /**
     * delete 请求
     *
     * @param url             URL
     * @param hashMap         请求头
     * @param object          对象,转成json
     * @param responseHandler 回调
     */
    public <T> void delete(String url, HashMap<String, String> hashMap, Object object, ObjectHttpResponseHandler<T> responseHandler) {
        String json = JsonUtil.parseObject2Str(object);
        // body 参数
        RequestBody body = RequestBody.create(JSON, json);
        // 请求头
        Headers headers = Headers.of(hashMap);
        // 创建一个请求
        Request request = new Request.Builder().url(DOMAIN + url).headers(headers).delete(body).build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(responseHandler);
    }

    /**
     * put 请求
     *
     * @param url             URL
     * @param hashMap         请求头
     * @param responseHandler 回调
     */
    public <T> void put(String url, HashMap<String, String> hashMap, ObjectHttpResponseHandler<T> responseHandler) {
        // body 参数
        RequestBody body = RequestBody.create(JSON, "{}");
        // 请求头
        Headers headers = Headers.of(hashMap);
        // 创建一个请求
        Request request = new Request.Builder().url(DOMAIN + url).headers(headers).put(body).build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(responseHandler);
    }

    /**
     * put 请求
     *
     * @param url             URL
     * @param object          对象,转成json
     * @param responseHandler 回调
     */
    public <T> void put(String url, Object object, ObjectHttpResponseHandler<T> responseHandler) {
        String json = JsonUtil.parseObject2Str(object);
        // body 参数
        RequestBody body = RequestBody.create(JSON, json);
        // 创建一个请求
        Request request = new Request.Builder().url(DOMAIN + url).put(body).build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(responseHandler);
    }

    /**
     * 判断是否有网络
     *
     * @param context
     * @return true:有网络，false：没有
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断是否有网络
     *
     * @return true:有网络，false：没有
     */
    public static boolean isNetworkConnected() {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * get请求
     *
     * @param url      无host的子URL
     * @param hashMap  headers
     * @param response 回调
     */
    public <T> void get(String url, HashMap<String, String> hashMap, ObjectHttpResponseHandler<T> response) {
        final Headers headers = Headers.of(hashMap);
        // 创建一个Request
        final Request request = new Request.Builder().url(DOMAIN + url).headers(headers).build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(response);
    }

    /**
     * delete请求
     *
     * @param url      无host的子URL
     * @param hashMap  headers
     * @param response 回调
     */
    public <T> void delete(String url, HashMap<String, String> hashMap, ObjectHttpResponseHandler<T> response) {
        final Headers headers = Headers.of(hashMap);
        // 创建一个Request
        final Request request = new Request.Builder().url(DOMAIN + url).headers(headers).delete().build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(response);
    }

    /**
     * get请求
     *
     * @param url      无host的子URL
     * @param hashMap  headers
     * @param params   url参数
     * @param response 回调
     */
    public <T> void get(String url, HashMap<String, String> hashMap, RequestUrlParams params, ObjectHttpResponseHandler<T> response) {
        if (params != null && params.isNotEmpty()) {
            url = getUrl(new StringBuffer(url), params);
        }
        final Headers headers = Headers.of(hashMap);
        // 创建一个Request
        final Request request = new Request.Builder().url(DOMAIN + url).headers(headers).build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(response);
    }

    /**
     * get请求 无header
     *
     * @param url      无host的子URL
     * @param response 回调
     */
    public <T> void get(String url, ObjectHttpResponseHandler<T> response) {
        // 创建一个Request
        final Request request = new Request.Builder().url(DOMAIN + url).build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(response);
    }

    /**
     * get请求 无header
     *
     * @param url      无host的子URL
     * @param params   url参数
     * @param response 回调
     */
    public <T> void get(String url, RequestUrlParams params, ObjectHttpResponseHandler<T> response) {
        if (params != null && params.isNotEmpty()) {
            url = getUrl(new StringBuffer(url), params);
        }
        // 创建一个Request
        final Request request = new Request.Builder().url(DOMAIN + url).build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(response);
    }

    /**
     * get请求 无header
     *
     * @param url      完整的URL
     * @param response 回调
     */
    public <T> void getAllUrl(String url, ObjectHttpResponseHandler<T> response) {
        // 创建一个Request
        final Request request = new Request.Builder().url(url).build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(response);
    }

    private String getUrl(StringBuffer sb, RequestUrlParams params) {
        sb.append("?");
        Set<String> keySet = params.getUrlParams().keySet();
        for (String key : keySet) {
            sb.append(key + "=");
            sb.append(params.getUrlParams().get(key));
            sb.append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public void get(String url, StringHttpResponseHandler response) {
        // 创建一个Request
        Request request = new Request.Builder().url(url).build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(response);
    }

    public void post(String url, FormBody body, StringHttpResponseHandler response) {
        Request request = new Request.Builder().url(url).post(body).build();
        // new call
        Call call = okHttpClient.newCall(request);
        // 加入调度
        call.enqueue(response);
    }
}
