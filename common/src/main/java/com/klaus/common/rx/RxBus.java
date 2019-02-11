package com.klaus.common.rx;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by caiyuk on 2019/1/10.
 */
public class RxBus {

    private static RxBus mRxBus;
    /**
     * String -> 事件key值
     * List<Subject> -> 集合中的观察者都在观察这个事件
     */
    private ConcurrentHashMap<String, List<Subject>> mSubjectMapper = new ConcurrentHashMap();

    private RxBus() {
    }

    public static RxBus getInstance() {

        if (mRxBus == null) {
            synchronized (RxBus.class) {
                if (mRxBus == null) {
                    mRxBus = new RxBus();
                }
            }
        }
        return mRxBus;
    }

    /**
     * 注册事件源
     *
     * @param tag
     * @param <T>
     * @return
     */
    public <T> Observable<T> register(@NonNull String tag) {

        List<Subject> subjectList = mSubjectMapper.get(tag);

        if (subjectList == null) {
            subjectList = new ArrayList<>();
            mSubjectMapper.put(tag, subjectList);
        }
        Subject<T> subject = PublishSubject.create();
        subjectList.add(subject);
        return subject;
    }

    /**
     * 解绑事件
     *
     * @param tag
     */
    public void unRegister(@NonNull String tag) {

        List<Subject> subjectList = mSubjectMapper.get(tag);
        if (subjectList != null) {
            subjectList.clear();
            mSubjectMapper.remove(tag);
        }
    }

    /**
     * 解绑指定观察者
     *
     * @param tag
     * @param observable
     */
    public void unRegister(@NonNull String tag, @NonNull Observable<?> observable) {

        if (observable == null) {
            return;
        }
        List<Subject> subjectList = mSubjectMapper.get(tag);
        if (!isEmpty(subjectList) && subjectList.contains(observable)) {
            subjectList.remove(observable);
            if (isEmpty(subjectList)) {
                mSubjectMapper.remove(tag);
            }
        }
    }

    /**
     * 触发事件
     *
     * @param tag     事件对应的key值
     * @param content 要传递出去的对象
     */
    public void post(@NonNull String tag, Object content) {

        List<Subject> subjects = mSubjectMapper.get(tag);
        if (!isEmpty(subjects)) {
            for (Subject subjuct : subjects) {
                subjuct.onNext(content);
            }
        }

    }


    private boolean isEmpty(Collection<Subject> collection) {
        return null == collection || collection.isEmpty();
    }
}
