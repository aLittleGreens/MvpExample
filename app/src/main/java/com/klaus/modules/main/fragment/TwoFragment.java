package com.klaus.modules.main.fragment;

import android.widget.TextView;

import com.klaus.R;
import com.klaus.common.base.BaseFragment;

import butterknife.Bind;

public class TwoFragment extends BaseFragment {

    @Bind(R.id.textView)
    TextView textView;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_two_layout;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        textView.setText("你好！");
    }
}
