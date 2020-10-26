package com.http.utils;

import com.http.constant.NetConstant;
import com.http.core.BaseObserver;
import com.http.core.BaseReponse;
import com.http.core.BaseRequest;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HttpUtils {

    public static boolean isRequestSuccess(int code){
        if(NetConstant.NetCode.SUCCESS == code){
            return true;
        }
        return false;
    }



}
