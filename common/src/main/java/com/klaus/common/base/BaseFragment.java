package com.klaus.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.klaus.common.commonutil.TUtil;
import com.klaus.common.commonwidget.LoadingDialog;
import com.klaus.common.rx.RxManager;

import butterknife.ButterKnife;

/**
 * Created by caiyuk on 2019/1/10.
 */
public abstract class BaseFragment<P extends BasePresenter,M extends BaseModel> extends Fragment {

    protected View rootView;
    public P mPresenter;
    public RxManager mRxManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutResource(), container, false);

        mRxManager=new RxManager();
        ButterKnife.bind(this, rootView);
        mPresenter = TUtil.getT(this, 0);
        M model = TUtil.getT(this, 1);
        if (mPresenter != null && model != null) {
            mPresenter.bindVM(this, model);
        }
//        initPresenter();
        initView();
        return rootView;

    }

    //获取布局文件
    protected abstract int getLayoutResource();
    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
//    public abstract void initPresenter();
    //初始化view
    protected abstract void initView();


    /**
     * 开启加载进度条
     */
    public void startProgressDialog() {
        LoadingDialog.showDialogForLoading(getActivity());
    }

    /**
     * 开启加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
        LoadingDialog.showDialogForLoading(getActivity(), msg, true);
    }

    /**
     * 停止加载进度条
     */
    public void stopProgressDialog() {
        LoadingDialog.cancelDialogForLoading();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (mPresenter != null)
            mPresenter.onDestroy();
        mRxManager.clear();
    }

}
