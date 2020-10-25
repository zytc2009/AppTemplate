package com.zy.baselib.lifecycle;

import android.app.Activity;
import android.content.Context;
import android.view.View;


import androidx.fragment.app.Fragment;


public abstract class SafeRunnable extends BaseLifeCircle implements Runnable {

    public SafeRunnable(Activity lifeCircle) {
        super(lifeCircle);
    }

    public SafeRunnable(Fragment lifeCircle) {
        super(lifeCircle);
    }

    public SafeRunnable(android.app.Fragment lifeCircle) {
        super(lifeCircle);
    }

    public SafeRunnable(Context lifeCircle) {
        super(lifeCircle);
    }

    public SafeRunnable(View lifeCircle) {
        super(lifeCircle);
    }

    protected abstract void runSafely();

    @Override
    public void run() {
        if (checkLifeCircle()) {
            runSafely();
        }
    }

}

