package com.klaus.modules.home.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.klaus.R;
import com.klaus.bean.WXarticle;
import com.klaus.common.base.BaseActivity;
import com.klaus.common.commonutil.ToastUitl;
import com.klaus.modules.home.contract.HomeContract;
import com.klaus.modules.home.model.HomeModel;
import com.klaus.modules.home.presenter.HomePresenter;

import butterknife.Bind;

public class HomeActivity extends BaseActivity<HomePresenter, HomeModel> implements HomeContract.View {
    private static final String TAG = "HomeActivity";
    @Bind(R.id.bt_request)
    Button bt_request;
    @Bind(R.id.textView)
    TextView textView;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initPresenter() {
        mPresenter.bindVM(this, mModel);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {
        bt_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getArticleRequest();
            }
        });
    }

    @Override
    public void returnArticle(WXarticle wXarticle) {
        ToastUitl.showShort(wXarticle.toString());
        textView.setText(wXarticle.toString());
    }

    @Override
    public void showLoading() {
        Log.e(TAG, "showLoading");
    }

    @Override
    public void stopLoading() {
        Log.e(TAG, "stopLoading");
    }

    @Override
    public void showErrorTip(String msg) {
        Log.e(TAG, "msg:" + msg);
    }
}
