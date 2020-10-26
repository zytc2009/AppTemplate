package com.ui.core;

import android.util.Log;

/**
 * Created by shishoufeng on 2019/12/13.
 * <p>
 * desc :  ui core 库 日志管理
 */
public class UICoreLog {

    private static IUICoreLogDelegateListener mLogDelegate = null;

    private static boolean isDebug = false;

    /**
     * 设置 日志代理
     *
     * @param delegate 代理实现类
     */
    public static void setDelegate(IUICoreLogDelegateListener delegate) {
        UICoreLog.mLogDelegate = delegate;
    }

    /**
     * 设置日志开关
     *
     * @param debug true 开启日志 false 不开启
     */
    public static void setDebug(boolean debug) {
        UICoreLog.isDebug = debug;
    }

    public static void e(String tag, String msg) {
        e(tag, msg, null);
    }

    public static void e(String tag, String msg, Throwable e) {
        if (mLogDelegate != null) {
            mLogDelegate.e(tag, msg, e);
            return;
        }
        if (isDebug) {
            Log.e(tag, msg, e);
        }
    }

    public static void w(String tag, String msg) {
        w(tag, msg, null);
    }

    public static void w(String tag, String msg, Throwable t) {
        if (mLogDelegate != null) {
            mLogDelegate.w(tag, msg, t);
            return;
        }
        if (isDebug) {
            Log.w(tag, msg, t);
        }
    }

    public static void i(String tag, String msg) {
        i(tag, msg,null);
    }

    public static void i(String tag, String msg,Throwable t) {
        if (mLogDelegate != null) {
            mLogDelegate.i(tag, msg,t);
            return;
        }
        if (isDebug) {
            Log.i(tag, msg,t);
        }
    }

    public static void d(String tag, String msg) {
        d(tag, msg, null);
    }

    public static void d(String tag, String msg, Throwable t) {
        if (mLogDelegate != null) {
            mLogDelegate.d(tag, msg, t);
            return;
        }
        if (isDebug) {
            Log.d(tag, msg, t);
        }
    }

    public interface IUICoreLogDelegateListener {

        void e(String tag, String msg, Throwable t);

        void w(String tag, String msg, Throwable t);

        void i(String tag, String msg, Throwable t);

        void d(String tag, String msg, Throwable t);

    }
}
