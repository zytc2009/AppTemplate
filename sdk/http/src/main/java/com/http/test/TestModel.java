package com.http.test;

import android.content.Context;
import android.util.Log;

import com.http.core.BaseReponse;
import com.http.core.BaseRequest;
import com.http.httpcall.HttpCall;
import com.http.httpcall.HttpCallback;

import java.util.List;

import retrofit2.Response;

public class TestModel {
    private Context context;

    public TestModel(Context context) {
        this.context = context;

    }


    public void getData() {
//        BaseRequest.getInstance().createService(ApiServise.class).getWangYiNews2("1", "5").subscribeOn(Schedulers.io()).
//                observeOn(AndroidSchedulers.mainThread()).subscribe(new BaseObserver<List<NewsBean>>(context) {
//            @Override
//            public void onSuccess(BaseReponse<List<NewsBean>> t) {
//                //成功回调方法,可以直接在此更新ui,AndroidSchedulers.mainThread()表示切换到主线程
//                Log.d("TestModel", "onSuccess() " +t.getResult());
//
//            }
//
//            @Override
//            public void onCodeError(BaseReponse baseReponse) {
//                //失败回调方法,可以直接在此更新ui,AndroidSchedulers.mainThread()表示切换到主线程
//                Log.d("TestModel", "onCodeError() ");
//            }
//
//            @Override
//            public void onFailure(Throwable e, boolean netWork) throws Exception {
//                Log.d("TestModel", "onFailure() "+e.getMessage());
//            }
//        });

//        BaseRequest.getInstance().createService(ApiServise.class).getWangYiNews("1", "5").enqueue(new Callback<BaseReponse<List<NewsBean>>>() {
//            @Override
//            public void onResponse(Call<BaseReponse<List<NewsBean>>> call, Response<BaseReponse<List<NewsBean>>> response) {
//                Log.d("TestModel", "onResponse() " + response.body().getResult());
//            }
//
//            @Override
//            public void onFailure(Call<BaseReponse<List<NewsBean>>> call, Throwable t) {
//                Log.d("TestModel", "onFailure() " + t.getMessage());
//            }
//        });

        BaseRequest.getInstance().createService(ApiServise.class).getWangYiNews3("1", "5").enqueue(new HttpCallback<BaseReponse<List<NewsBean>>>() {
            @Override
            public void success(Response<BaseReponse<List<NewsBean>>> response, HttpCall<BaseReponse<List<NewsBean>>> httpCall) {
                Log.d("TestModel", "success() " + response.body().getResult());
            }

            @Override
            public void clientError(Response<BaseReponse<List<NewsBean>>> response, HttpCall<BaseReponse<List<NewsBean>>> httpCall) {
                Log.d("TestModel", "clientError() " + response.body().getResult());
            }

            @Override
            public void networkError(Throwable t, HttpCall<BaseReponse<List<NewsBean>>> httpCall) {
                Log.d("TestModel", "networkError() " + t.getMessage());
            }

        });
    }
}
