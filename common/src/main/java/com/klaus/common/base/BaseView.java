package com.klaus.common.base;

/**
 * Created by caiyuk on 2019/1/10.
 */
public interface BaseView {
    /*******内嵌加载*******/
    void showLoading();
    void stopLoading();
    void showErrorTip(String msg);
}
