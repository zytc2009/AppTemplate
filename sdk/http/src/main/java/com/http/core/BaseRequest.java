package com.http.core;

import com.http.test.ApiServise;
import com.http.httpcall.HttpCallAdapterFactory;
import com.http.interceptor.InterceptorUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 通过初始化okhttp和retrofit,获取ApiServise的实例
         * 主要做retrofit绑定okhttp,然后通过retrofit的实例
         * 获取ApiServise的实例
         */
public class BaseRequest {
    //初始化Okhttp,绑定拦截器事件
    OkHttpClient client=new OkHttpClient.Builder().
            connectTimeout(20, TimeUnit.SECONDS).                   //设置请求超时时间
            readTimeout(20,TimeUnit.SECONDS).                       //设置读取数据超时时间
            writeTimeout(20,TimeUnit.SECONDS).                      //设置写入数据超时时间
            addInterceptor(InterceptorUtil.LogInterceptor()).                //绑定日志拦截器
            addNetworkInterceptor(InterceptorUtil.HeaderInterceptor())       //绑定header拦截器
            .build();


    Retrofit retrofit=new Retrofit.Builder().
            addConverterFactory(GsonConverterFactory.create()).             //设置gson转换器,将返回的json数据转为实体
            addCallAdapterFactory(RxJavaCallAdapterFactory.create()).       //设置CallAdapter
            addCallAdapterFactory(HttpCallAdapterFactory.create()).
            baseUrl(ApiServise.HOST).
            client(client)                                                  //设置客户端okhttp相关参数
            .build();


    public static BaseRequest instance;


    private BaseRequest(){

    }
    public static BaseRequest getInstance(){
        if(instance==null){
            synchronized (BaseRequest.class){
                if(instance==null){
                    instance=new BaseRequest();
                }
            }
        }
        return instance;
    }


    public <S> S createService(Class<S> cls) {
        return retrofit.create(cls);
    }

}
