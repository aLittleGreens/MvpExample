package com.klaus.modules.main.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.klaus.R;
import com.klaus.common.base.BaseActivity;
import com.klaus.modules.main.fragment.ArticleFragment;
import com.klaus.modules.main.fragment.TwoFragment;

import butterknife.Bind;

public class MainTabActivity extends BaseActivity {

    private static final String TAG = "MainTabActivity";
    @Bind(R.id.navigation)
    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.article:
                    switchTo(0);
                    return true;
                case R.id.two:
                    switchTo(1);
                    return true;
            }
            return false;
        }
    };
    private ArticleFragment mArticleFragment;
    private TwoFragment mTwoFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState) {


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            mArticleFragment = (ArticleFragment) getSupportFragmentManager().findFragmentByTag("mArticleFragment");
            mTwoFragment = (TwoFragment) getSupportFragmentManager().findFragmentByTag("mTwoFragment");
        } else {
            mArticleFragment = new ArticleFragment();
            mTwoFragment = new TwoFragment();
            transaction.add(R.id.fl_body, mArticleFragment, "mArticleFragment");
            transaction.add(R.id.fl_body, mTwoFragment, "mTwoFragment");
        }
        transaction.commit();
        switchTo(currentTabPosition);
    }

    private void switchTo(int position) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                transaction.show(mArticleFragment);
                transaction.hide(mTwoFragment);
                transaction.commitAllowingStateLoss();
                break;
            case 1:
                transaction.show(mTwoFragment);
                transaction.hide(mArticleFragment);
                transaction.commitAllowingStateLoss();
                break;

        }
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_main_tab;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void initEvent() {

    }
}
