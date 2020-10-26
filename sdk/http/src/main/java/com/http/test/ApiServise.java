package com.http.test;

import com.http.core.BaseReponse;
import com.http.httpcall.HttpCall;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 请求方法类
  */
public interface ApiServise {
    //https://api.apiopen.top/getWangYiNews  接口完整路径
    /**
     * get请求方式
     * @Query
     * 形成单个查询参数, 将接口url中追加类似于"page=1"的字符串,形成提交给服务器端的参数,
     * 主要用于Get请求数据，用于拼接在拼接在url路径后面的查询参数，一个@Query相当于拼接一个参数
     */
    String HOST = "https://api.apiopen.top";        //测试接口地址

    @FormUrlEncoded
    @POST("/getWangYiNews")
    Call<BaseReponse<List<NewsBean>>> getWangYiNews(@Field("page") String page, @Field("count") String count);

    /**
     * post请求方式
     */
    @FormUrlEncoded         //post请求必须要申明该注解
    @POST("/getWangYiNews")   //方法名
    Observable<BaseReponse<List<NewsBean>>> getWangYiNews2(@Field("page") String page,  @Field("count") String count);//请求参数


    /**
     * post请求方式
     */
    @FormUrlEncoded         //post请求必须要申明该注解
    @POST("/getWangYiNews")   //方法名
    HttpCall<BaseReponse<List<NewsBean>>> getWangYiNews3(@Field("page") String page, @Field("count") String count);//请求参数
}
