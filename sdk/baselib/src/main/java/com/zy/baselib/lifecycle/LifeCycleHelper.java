package com.zy.baselib.lifecycle;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LifeCycleHelper {
    public static boolean checkLifeCircle(@Nullable Fragment fragment) {
        if (fragment == null || fragment.isRemoving() || fragment.isDetached() || !fragment.isAdded()) {
            return false;
        }
        return checkLifeCircle(fragment.getActivity());
    }

    public static boolean checkLifeCircle(@Nullable android.app.Fragment fragment) {
        if (fragment == null || fragment.isRemoving() || fragment.isDetached() || !fragment.isAdded()) {
            return false;
        }
        return checkLifeCircle(fragment.getActivity());
    }

    public static boolean checkLifeCircle(@Nullable Context context) {
        if (context == null) {
            return false;
        }
        Activity activity = findActivity(context);
        return checkLifeCircle(activity);
    }

    public static boolean checkLifeCircle(@Nullable View view) {
        if (view == null || view.getContext() == null) {
            return false;
        }
        Activity activity = findActivity(view.getContext());
        return checkLifeCircle(activity);
    }

    public static boolean checkLifeCircle(@Nullable Activity activity) {
        return activity != null && !activity.isFinishing() && !activity.isDestroyed();
    }

    public static Activity findActivity(@Nullable Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return findActivity(((ContextWrapper) context).getBaseContext());
        } else {
            return null;
        }
    }
}
