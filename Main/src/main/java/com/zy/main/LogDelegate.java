package com.zy.main;

import android.util.Log;

import com.arch.UtilsLog;
import com.ui.core.UICoreLog;

/**
 * Created by shishoufeng on 2019/12/13.
 * <p>
 * desc :
 */
public class LogDelegate implements UtilsLog.IUtilsLogDelegateListener, UICoreLog.IUICoreLogDelegateListener {

    private static final String TAG = "LogDelegate ->";


    @Override
    public void e(String tag, String msg, Throwable t) {
        Log.e(TAG+tag, "e : "+msg,t);
    }

    @Override
    public void w(String tag, String msg, Throwable t) {
        Log.w(TAG+tag, "w: "+msg,t);
    }

    @Override
    public void i(String tag, String msg, Throwable t) {
        Log.i(TAG+tag, "i: "+msg,t);

    }

    @Override
    public void d(String tag, String msg, Throwable t) {
        Log.d(TAG+tag, "d: "+msg,t);
    }
}
