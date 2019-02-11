package com.klaus.modules.main.present;

import com.klaus.bean.WXarticle;
import com.klaus.modules.main.contract.ArticleContract;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by caiyuk on 2019/1/30.
 */

public class ArticlePresenter extends ArticleContract.Presenter {

    @Override
    public void getArticleRequest() {
        mRxManager.add(mModel.getArticle().subscribe(new Consumer<WXarticle>() {
            @Override
            public void accept(WXarticle wXarticle) throws Exception {
                mView.returnArticle(wXarticle);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.showErrorTip("throwable:"+throwable.getLocalizedMessage());
                mView.stopLoading();
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                mView.stopLoading();
            }
        }, new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                mView.showLoading();
            }
        }));
    }
}
