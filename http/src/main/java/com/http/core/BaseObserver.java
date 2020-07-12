package com.http.core;

import android.accounts.NetworkErrorException;
import android.content.Context;

import com.http.utils.HttpUtils;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import rx.Observer;

/**
 * 请求回调,主要实现Observer类
 * @param <T>
 */
public abstract class BaseObserver<T> implements Observer<BaseReponse<T>> {
    private Context mContext;

    public BaseObserver(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ConnectException ||
                e instanceof TimeoutException ||
                e instanceof NetworkErrorException ||
                e instanceof UnknownHostException) {
            try {
                onFailure(e, false);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else {
            try {
                onFailure(e, true);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void onNext(BaseReponse<T> tBaseReponse) {
        if (HttpUtils.isRequestSuccess(tBaseReponse.getCode())) {
            onSuccess(tBaseReponse);
        } else {
            onCodeError(tBaseReponse);
        }
    }

    //请求成功且返回码为200的回调方法,这里抽象方法申明
    public abstract void onSuccess(BaseReponse<T> tBaseReponse);

    //请求成功但返回的code码不是200的回调方法,这里抽象方法申明
    public abstract void onCodeError(BaseReponse tBaseReponse);

    //请求失败回调方法,这里抽象方法申明
    public abstract void onFailure(Throwable e, boolean netWork) throws Exception;
}
