package com.klaus.modules.home.contract;

import android.view.View;

import com.klaus.bean.WXarticle;
import com.klaus.common.base.BaseModel;
import com.klaus.common.base.BasePresenter;
import com.klaus.common.base.BaseView;

import io.reactivex.Observable;

/**
 * Created by caiyuk on 2019/1/30.
 */

public interface HomeContract {

    interface View extends BaseView {
        public void returnArticle(WXarticle wXarticle);
    }

    interface Model extends BaseModel {
        public Observable<WXarticle> getArticle();
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getArticleRequest();
    }


}
