package com.arch;

import android.util.Log;

/**
 * Created by shishoufeng on 2019/12/10.
 * <p>
 * desc :  工具库 日志篇
 */
public final class UtilsLog {



    private static IUtilsLogDelegateListener mLogDelegate = null;

    private static boolean isDebug = false;

    /**
     * 设置 日志代理
     *
     * @param delegate 代理实现类
     */
    public static void setDelegate(IUtilsLogDelegateListener delegate) {
        UtilsLog.mLogDelegate = delegate;
    }

    /**
     * 设置日志开关
     *
     * @param debug true 开启日志 false 不开启
     */
    public static void setDebug(boolean debug) {
        UtilsLog.isDebug = debug;
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

    public interface IUtilsLogDelegateListener {

        void e(String tag, String msg, Throwable t);

        void w(String tag, String msg, Throwable t);

        void i(String tag, String msg, Throwable t);

        void d(String tag, String msg, Throwable t);

    }




}
