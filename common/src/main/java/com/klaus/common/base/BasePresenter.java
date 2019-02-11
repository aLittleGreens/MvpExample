package com.klaus.common.base;

import android.content.Context;

import com.klaus.common.rx.RxManager;

/**
 * Created by caiyuk on 2019/1/10.
 */

public abstract class BasePresenter<V, M> {

    protected Context mContext;
    protected V mView;
    protected M mModel;

    protected RxManager mRxManager = new RxManager();

    public void bindVM(V v, M m) {
        this.mView = v;
        this.mModel = m;
    }

    public void onDestroy() {
        mRxManager.clear();
    }


}
