package com.http.httpcall;

import android.os.Handler;
import android.os.Looper;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

import static com.http.httpcall.Types.checkNotNull;

public class HttpCallAdapterFactory<R> extends CallAdapter.Factory {

    /* access modifiers changed from: private */
    public MainThreadExecutor mMainThreadExecutor = new MainThreadExecutor();

    private HttpCallAdapterFactory() {
    }


    public static HttpCallAdapterFactory create() {
        return new HttpCallAdapterFactory();
    }

    public CallAdapter<?, ?> get(Type type, Annotation[] annotationArr, Retrofit retrofit) {
        if (Types.getRawType(type) != HttpCall.class) {
            return null;
        }
        if (type instanceof ParameterizedType) {
            final Type parameterUpperBound = getParameterUpperBound(0, (ParameterizedType) type);
            return new CallAdapter<R, HttpCall<?>>() {
                public Type responseType() {
                    return parameterUpperBound;
                }

                @Override
                public HttpCall<?> adapt(Call<R> call) {
                    HttpCallAdapter httpCallAdapter = new HttpCallAdapter(call, HttpCallAdapterFactory.this.mMainThreadExecutor);
                    return httpCallAdapter;
                }
            };
        }
        throw new IllegalStateException("HttpCall must have generic type (e.g., HttpCall<ResponseBody>)");
    }

    class MainThreadExecutor implements Executor {
        private Handler mainHandler = new Handler(Looper.getMainLooper());

        MainThreadExecutor() {
        }

        public void execute(Runnable runnable) {
            this.mainHandler.post((Runnable) checkNotNull(runnable, "command == null"));
        }
    }
}