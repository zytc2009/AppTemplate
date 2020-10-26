package com.arch.utils;

import android.app.Activity;
import android.content.Intent;

import com.arch.ArchUtils;

import androidx.annotation.NonNull;

/**
 * Created by shishoufeng on 2020-02-07.
 * email:shishoufeng1227@126.com
 *
 *
 * activity 相关操作工具类
 *
 */
public final class ActivityUtils {

    private ActivityUtils() {
    }


    /**
     * Return whether the activity exists.
     *
     * @param pkg The name of the package.
     * @param cls The name of the class.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isActivityExists(@NonNull final String pkg,
                                           @NonNull final String cls) {
        Intent intent = new Intent();
        intent.setClassName(pkg, cls);
        return !(ArchUtils.getContext().getPackageManager().resolveActivity(intent, 0) == null ||
                intent.resolveActivity(ArchUtils.getContext().getPackageManager()) == null ||
                ArchUtils.getContext().getPackageManager().queryIntentActivities(intent, 0).size() == 0);
    }

    /**
     *
     * return the activity destroy
     *
     * @param activity activity instance
     * @return true is destroy
     */
    public static boolean isActivityDestroy(Activity activity){
        return activity == null || activity.isFinishing() || activity.isDestroyed();
    }





}
