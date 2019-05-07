package com.klaus;

import android.content.Intent;

import com.klaus.common.base.BaseActivity;
import com.klaus.common.rx.RxManager;
import com.klaus.modules.home.activity.HomeActivity;
import com.klaus.modules.main.activity.MainTabActivity;


import butterknife.OnClick;
import io.reactivex.functions.Consumer;


public class MainActivity extends BaseActivity {

    private static final String TAG = "com.klaus.MainActivity";


    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
       mRxManager.on("klaus", new Consumer<Integer>() {
           @Override
           public void accept(Integer integer) throws Exception {

           }
       });
    }

    @Override
    protected void initEvent() {

    }

    @OnClick(R.id.button)   //给 button1 设置一个点击事件
    public void skip() {
        startActivity(new Intent(this, HomeActivity.class));
    }

    @OnClick(R.id.button1)   //给 button1 设置一个点击事件
    public void skip1() {
        startActivity(new Intent(this, MainTabActivity.class));
    }

}
