package com.http.httpcall;

import java.io.IOException;
import okhttp3.Request;
import retrofit2.Response;

public interface HttpCall<T> extends Cloneable {
    void cancel();

    HttpCall<T> clone();

    Response<T> execute() throws IOException;

    boolean isCanceled();

    boolean isExecuted();

    Request request();

    void enqueue(HttpCallback<T> callback);
}