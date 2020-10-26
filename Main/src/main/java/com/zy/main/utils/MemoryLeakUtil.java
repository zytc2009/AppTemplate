package com.zy.main.utils;

import android.app.Application;
import android.content.Context;

import com.kwai.koom.javaoom.KOOM;
import com.kwai.koom.javaoom.common.KConfig;
import com.kwai.koom.javaoom.common.KLog;
import com.kwai.koom.javaoom.dump.ForkJvmHeapDumper;

import java.io.File;

//KOOM
public class MemoryLeakUtil {

    public static void init(Application application){
        KOOM.init(application);
    }

    //手动触发检查
    public static void manualTrigger(){
        KOOM.getInstance().manualTrigger();
    }

    public static void manualTriggerOnCrash() {
        KOOM.getInstance().manualTriggerOnCrash();
    }

    //Example of how to get report manually.
    public static void getReportManually() {
        File reportDir = new File(KOOM.getInstance().getReportDir());
        for (File report : reportDir.listFiles()) {
            //Upload the report or do something else.
        }
    }

    //Example of how to listen report's generate status.
    public static void listenReportGenerateStatus() {
        KOOM.getInstance().setHeapReportUploader(file -> {
            //Upload the report or do something else.
            //File is deleted automatically when callback is done by default.
        });
    }

    //Example of how to set custom config.
    public static void customConfig(Context context) {
        KConfig kConfig = new KConfig.KConfigBuilder()
                .heapRatio(85.0f) //heap occupied ration in percent, 85.0f means use 85% memory of max heap
                .rootDir(context.getCacheDir().getAbsolutePath()) //root dir stores report and hprof files
                .heapOverTimes(3) //heap max times of over heap's used threshold
                .build();
        KOOM.getInstance().setKConfig(kConfig);
    }

    //Example of how to set custom logger.
    public static void customLogger() {
        KOOM.getInstance().setLogger(new KLog.KLogger() {
            @Override
            public void i(String TAG, String msg) {
                //get the log of info level
            }

            @Override
            public void d(String TAG, String msg) {
                //get the log of debug level
            }

            @Override
            public void e(String TAG, String msg) {
                //get the log of error level
            }
        });
    }

    //Example of set custom koom root dir.
    public static void customRootDir(Context context) {
        KOOM.getInstance().setRootDir(context.getCacheDir().getAbsolutePath());
    }

    //Example of dump hprof directly
    public static void customDump() {
        //Same with StandardHeapDumper StripHprofHeapDumper
        new ForkJvmHeapDumper().dump("absolute-path");
    }
}
