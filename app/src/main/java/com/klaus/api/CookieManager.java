package com.klaus.api;

import android.content.Context;

import com.klaus.api.cookie.PersistentCookieJar;
import com.klaus.api.cookie.cache.SetCookieCache;
import com.klaus.api.cookie.persistence.SharedPrefsCookiePersistor;

/**
 * @author LittleGreens <a href="mailto:alittlegreens@foxmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019/9/6 14:12
 */
public class CookieManager {

    private static CookieManager instance = null;

    private PersistentCookieJar cookieJar = null;

    public PersistentCookieJar getCookieJar() {
        return cookieJar;
    }

    public void clearCookieInfo() {
        cookieJar.clear();
    }

    private CookieManager(Context context) {
        cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
    }

    public static CookieManager getInstance(Context context) {
        if (instance == null) {
            instance = new CookieManager(context);
        }
        return instance;
    }
}
