package com.arch;

import android.annotation.SuppressLint;
import android.content.Context;

import com.arch.utils.thread.ThreadPool;

import androidx.core.content.FileProvider;

/**
 * Created by shishoufeng on 2019/12/17.
 * <p>
 * desc : 工具库 辅助类
 */
public final class ArchUtils {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;


    public static void initialization(Context context){
        if (context == null){
            return;
        }
        mContext = context.getApplicationContext();

        ThreadPool.init();
    }

    public static Context getContext(){
        return mContext;
    }

    public static class ArchProvider extends FileProvider{

        @Override
        public boolean onCreate() {
            initialization(getContext());
            return super.onCreate();
        }
    }

}
