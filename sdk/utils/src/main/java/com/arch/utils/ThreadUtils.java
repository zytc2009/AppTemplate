package com.arch.utils;

import android.os.Looper;

import com.arch.UtilsLog;
import com.zy.baselib.CommonConstants;

/**
 *
 * 线程相关工具类
 */
public final class ThreadUtils {

    public static void ensureUiThread() {
        if (!ThreadUtils.isUiThread()) {
            IllegalStateException tr = new IllegalStateException("ensureUiThread: thread check failed");
            if (CommonConstants.IS_DEBUG) {
                //throw tr;
//                throw new RuntimeException();
            } else {
                UtilsLog.e("ThreadUtils", tr ,"ensureUiThread: thread check failed");
            }
        }
    }

    /**
     * 检查是否非ui线程
     */
    public static void ensureNonUiThread() {
        if (ThreadUtils.isUiThread()) {
            IllegalStateException tr = new IllegalStateException("ensureNonUiThread: thread check failed");
            if (CommonConstants.IS_DEBUG) {
                throw tr;
            } else {
                UtilsLog.e("ThreadUtils", tr , "ensureNonUiThread: thread check failed");
            }
        }
    }

    public static boolean isUiThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }


}
