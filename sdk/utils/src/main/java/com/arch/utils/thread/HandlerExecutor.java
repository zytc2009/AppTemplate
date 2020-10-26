package com.arch.utils.thread;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

/**
 * 代理类
 */
public class HandlerExecutor implements ITaskExecutor {
    private Handler mTaskHandler;

    public HandlerExecutor(@NonNull Handler handler) {
        mTaskHandler = handler;
    }

    @Override
    public void post(@NonNull Runnable task) {
        mTaskHandler.post(task);
    }

    public static HandlerExecutor withMainLooper() {
        return new HandlerExecutor(new Handler(Looper.getMainLooper()));
    }
}
