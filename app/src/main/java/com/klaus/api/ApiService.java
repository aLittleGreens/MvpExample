package com.klaus.api;


import com.klaus.bean.WXarticle;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author zhoujin
 * @date 2017/7/3
 */

public interface ApiService {

    /**
     * 可监控下载进度
     *
     * @param downloadUrl
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String downloadUrl);


    /**
     * 参数动态更新请求URL。替换块是由{和包围的字母数字字符串}。
     * 必须@Path使用相同的字符串注释相应的参数。
     *
     * @param id 可动态替换
     */
    @GET("wxarticle/{id}/{json}")
    Observable<WXarticle> getArticle(@Path("id") String id);


    /**
     * post 和 get 都可以传全路径
     * 缺点 路径是写死的
     *
     * @return
     */
    @GET("http://wanandroid.com/wxarticle/chapters/json")
    Observable<WXarticle> getArticle();

    /**
     * post 和 get 都可以传全路径
     *
     * @param url 可以动态改变
     */
    @Headers("Cache-Control:public ,max-age=60")
    @GET()
    Observable<WXarticle> getArticleUrl(@Url String url);

    /**
     * 也可以用拦截器拦截head，不需要改变URl的不需要设置，拦截器通过拦截head，重新设置URL
     */
}
