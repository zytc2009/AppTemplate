package com.http.httpcall;

import retrofit2.Response;

public interface HttpCallback<T> {
    void success(Response<T> response, HttpCall<T> httpCall);

    void clientError(Response<T> response, HttpCall<T> httpCall);

    void networkError(Throwable iOException, HttpCall<T> httpCall);
}