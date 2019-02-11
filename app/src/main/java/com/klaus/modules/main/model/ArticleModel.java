package com.klaus.modules.main.model;

import com.klaus.api.Api;
import com.klaus.bean.WXarticle;
import com.klaus.modules.main.contract.ArticleContract;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by caiyuk on 2019/1/30.
 */

public class ArticleModel implements ArticleContract.Model {
    @Override
    public Observable<WXarticle> getArticle() {
        return Api.getInstance().getArticle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
