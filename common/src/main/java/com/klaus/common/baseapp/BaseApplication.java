package com.klaus.common.baseapp;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDex;

/**
 * Created by caiyuk on 2019/1/10.
 */
public class BaseApplication extends Application {

    private static BaseApplication baseApplication;
    public static boolean isMqtt = false;
    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
    }

    public static Context getAppContext() {
        return baseApplication;
    }
    public static Resources getAppResources() {
        return baseApplication.getResources();
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 分包
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
