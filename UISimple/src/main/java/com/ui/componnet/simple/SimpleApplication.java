package com.ui.componnet.simple;

import android.app.Application;

import com.arch.ArchUtils;

/**
 * Created by shishoufeng on 2019/12/17.
 * <p>
 * desc :
 */
public class SimpleApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        ArchUtils.initialization(this);


    }
}
