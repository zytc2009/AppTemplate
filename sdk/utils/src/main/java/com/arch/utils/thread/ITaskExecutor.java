package com.arch.utils.thread;

import androidx.annotation.NonNull;

public interface ITaskExecutor {
    void post(@NonNull Runnable task);
}
