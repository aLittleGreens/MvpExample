package com.klaus.modules.main.fragment;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.klaus.R;
import com.klaus.api.Api;
import com.klaus.bean.LoginBean;
import com.klaus.bean.LoginData;
import com.klaus.common.base.BaseFragment;

import butterknife.Bind;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TwoFragment extends BaseFragment {

    @Bind(R.id.textView)
    TextView textView;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_two_layout;
    }


    @Override
    protected void initView() {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api.getInstance().login("alittlegreens","cai784921129")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<LoginBean>() {
                            @Override
                            public void accept(LoginBean loginBean) throws Exception {
                                LoginData loginData = loginBean.getT();
                                Log.e("login", "accept: " + loginData);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                throwable.printStackTrace();
                            }
                        });
            }
        });
    }
}
