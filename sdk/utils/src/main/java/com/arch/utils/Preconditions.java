package com.arch.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressWarnings("unused")
public class Preconditions {
    private Preconditions() {
    }

    public static void checkMainThread() {
        if (!ThreadUtils.isUiThread()) {
            throw new RuntimeException("Not in main thread");
        }
    }

    public static void checkNonMainThread() {
        if (ThreadUtils.isUiThread()) {
            throw new RuntimeException("In main thread");
        }
    }

    public static @NonNull <T> T checkNotNull(@Nullable T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }

    public static @NonNull <T> T checkNotNull(@Nullable T obj, @NonNull String errorMessage) {
        if (obj == null) {
            throw new NullPointerException(errorMessage);
        }
        return obj;
    }

    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkArgument(boolean expression, @NonNull String errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void checkState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }

    public static void checkState(boolean expression, @NonNull String errorMessage) {
        if (!expression) {
            throw new IllegalStateException(errorMessage);
        }
    }
}
