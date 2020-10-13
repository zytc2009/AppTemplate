package com.app.template;

import android.app.Application;

import com.app.template.utils.MemoryLeakUtil;
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
        MemoryLeakUtil.init(this);
        ArchUtils.initialization(this);
    }
}
