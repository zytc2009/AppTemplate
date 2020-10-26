package com.zy.baselib.lifecycle;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import java.lang.ref.WeakReference;

import androidx.fragment.app.Fragment;


public abstract class BaseLifeCircle {

    private WeakReference<Object> lifeCircleWeakReference;

    public BaseLifeCircle(Activity lifeCircle) {
        init(lifeCircle);
    }

    public BaseLifeCircle(Fragment lifeCircle) {
        init(lifeCircle);
    }

    public BaseLifeCircle(android.app.Fragment lifeCircle) {
        init(lifeCircle);
    }

    public BaseLifeCircle(Context lifeCircle) {
        init(lifeCircle);
    }

    public BaseLifeCircle(View lifeCircle) {
        init(lifeCircle);
    }

    private <T> void init(T lifeCircle) {
        this.lifeCircleWeakReference = (WeakReference<Object>) new WeakReference<>(lifeCircle);
    }

    final public boolean checkLifeCircle() {
        Object t = lifeCircleWeakReference.get();
        if (t instanceof Activity) {
            return LifeCycleHelper.checkLifeCircle((Activity) t);
        } else if (t instanceof Fragment) {
            return LifeCycleHelper.checkLifeCircle((Fragment) t);
        } else if (t instanceof android.app.Fragment) {
            return LifeCycleHelper.checkLifeCircle((android.app.Fragment) t);
        } else if (t instanceof View) {
            return LifeCycleHelper.checkLifeCircle((View) t);
        } else if (t instanceof Context) {
            return LifeCycleHelper.checkLifeCircle((Context) t);
        }
        return false;
    }

    public Object get() {
        if (lifeCircleWeakReference == null) {
            return null;
        }
        return lifeCircleWeakReference.get();
    }

    public void clear() {
        if (lifeCircleWeakReference != null) {
            lifeCircleWeakReference.clear();
        }
    }
}
