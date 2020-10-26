package com.http.httpcall;

import android.util.Log;

import com.http.utils.HttpUtils;

import java.io.IOException;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.http.httpcall.Types.checkNotNull;

public class HttpCallAdapter<T> implements HttpCall<T> {

    private Call<T> mDelegate;
    /* access modifiers changed from: private */
    public HttpCallAdapterFactory.MainThreadExecutor mMainThreadExecutor;


    public HttpCallAdapter(Call<T> call, HttpCallAdapterFactory.MainThreadExecutor mainThreadExecutor) {
        this.mDelegate = call;
        this.mMainThreadExecutor = mainThreadExecutor;
    }


    public Response<T> execute() throws IOException {
        return this.mDelegate.execute();
    }

    public void enqueue(final HttpCallback<T> httpCallback) {
        checkNotNull(httpCallback, "callback == null");
        this.mDelegate.enqueue(new Callback<T>() {
            public void onResponse(Call<T> call, final Response<T> response) {
                HttpCallAdapter.this.mMainThreadExecutor.execute(new Runnable() {
                    public void run() {
                        if (httpCallback == null) {
                            Log.e("HttpService", "callback is null when onResponse invoke");
                            return;
                        }
                        int code = response.code();
                        if (HttpUtils.isRequestSuccess(code)) {
                            T body = response.body();
                            if (!response.isSuccessful()) {
                                return;
                            }
                            httpCallback.success(response, HttpCallAdapter.this);
                        } else{
                            httpCallback.clientError(response, HttpCallAdapter.this);
                        }
                    }
                });
            }

            public void onFailure(Call<T> call, final Throwable th) {
                HttpCallAdapter.this.mMainThreadExecutor.execute(new Runnable() {
                    public void run() {
                            httpCallback.networkError(th, HttpCallAdapter.this);
                    }
                });
            }
        });
    }

    public void cancel() {
        this.mDelegate.cancel();
    }

    public boolean isExecuted() {
        return this.mDelegate.isExecuted();
    }

    public boolean isCanceled() {
        return this.mDelegate.isCanceled();
    }

    public HttpCall<T> clone() {
        return new HttpCallAdapter(this.mDelegate.clone(), this.mMainThreadExecutor);
    }

    public Request request() {
        return this.mDelegate.request();
    }
}