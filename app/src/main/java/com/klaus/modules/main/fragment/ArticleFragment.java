package com.klaus.modules.main.fragment;

import android.widget.ListView;

import com.klaus.R;
import com.klaus.adapter.ArticleAdapter;
import com.klaus.bean.WXarticle;
import com.klaus.common.base.BaseFragment;
import com.klaus.common.commonutil.ToastUitl;
import com.klaus.modules.main.contract.ArticleContract;
import com.klaus.modules.main.model.ArticleModel;
import com.klaus.modules.main.present.ArticlePresenter;

import java.util.List;

import butterknife.Bind;

public class ArticleFragment extends BaseFragment<ArticlePresenter, ArticleModel> implements ArticleContract.View {

    @Bind(R.id.listView)
    ListView listView;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_article_layout;
    }

    @Override
    public void initPresenter() {
        mPresenter.bindVM(this, mModel);
    }

    @Override
    protected void initView() {
        mPresenter.getArticleRequest();
    }

    @Override
    public void returnArticle(WXarticle wXarticle) {
        ToastUitl.showShort(wXarticle.toString());
        if(wXarticle.getErrorCode() == 0){
            List<WXarticle.DataBean> data = wXarticle.getData();
            ArticleAdapter articleAdapter = new ArticleAdapter(getContext(),data);
            listView.setAdapter(articleAdapter);
        }
    }

    @Override
    public void showLoading() {
        startProgressDialog();
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @Override
    public void showErrorTip(String msg) {
        ToastUitl.showShort(msg);
    }
}
