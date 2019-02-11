package com.klaus.common.rx;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by caiyuk on 2019/1/10.
 */
public class RxManager {

    private RxBus mRxBus = RxBus.getInstance();
    /**
     * 管理上下游订阅关系
     */
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    /**
     * 管理Rxbus订阅
     */
    private Map<String, Observable<?>> mObservableMap = new HashMap<>();

    /**
     * 注入事件监听
     *
     * @param eventName
     * @param consumer
     * @param <T>
     */
    public <T> void on(@NonNull String eventName, @NonNull Consumer<T> consumer) {
        Observable<T> observable = mRxBus.register(eventName);
        mObservableMap.put(eventName,observable);
        mCompositeDisposable.add(observable.observeOn(AndroidSchedulers.mainThread()).subscribe(consumer, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        }));

    }

    /**
     * 收集这个Disposable，便于终断上下游事件
     *
     * @param m
     */
    public void add(@NonNull Disposable m) {
        /*订阅管理*/
        mCompositeDisposable.add(m);
    }

    /**
     * 生命周期结束，取消订阅和所有Rxbus观察
     */
    public void clear() {
        mCompositeDisposable.dispose();//切断订阅事件
        for (Map.Entry<String, Observable<?>> entry : mObservableMap.entrySet()) {
            // 移除rxbus观察
            mRxBus.unRegister(entry.getKey(), entry.getValue());
        }
    }

}
