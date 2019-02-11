package com.klaus.modules.home.model;

import com.klaus.api.Api;
import com.klaus.bean.WXarticle;
import com.klaus.common.rx.RxSchedulers;
import com.klaus.modules.home.contract.HomeContract;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by caiyuk on 2019/1/30.
 */

public class HomeModel implements HomeContract.Model {
    @Override
    public Observable<WXarticle> getArticle() {
        return Api.getInstance().getArticle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
