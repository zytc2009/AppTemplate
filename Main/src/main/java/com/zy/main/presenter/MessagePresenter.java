package com.zy.main.presenter;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

import com.zy.main.utils.InputMethodUtils;
import com.ui.core.model.IBaseModel;
import com.ui.core.presenter.BasePresenter;

import androidx.annotation.RequiresApi;

public class MessagePresenter extends BasePresenter {
    private boolean inputMethodOpen;
    //需要存sp
    private static int height;

    public MessagePresenter() {

    }


    public void registerKeyboardListener(final View rootView) {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onGlobalLayout() {
                boolean inputMethodOpenNow = InputMethodUtils.checkKeyBoardOpen(rootView, new InputMethodUtils.OnKeyBoardOpenListener() {
                    @Override
                    public void getKeyBoardHeight(int heightOfKeyBoard) {
                        height = heightOfKeyBoard;
                    }
                });
                if (inputMethodOpen != inputMethodOpenNow) {
                    inputMethodOpen = inputMethodOpenNow;
                    if (inputMethodOpen) {

                    }
                }
            }
        });
    }


    public int getKeyboardHeight() {
        return height;
    }

    @Override
    protected IBaseModel initModel() {
        return null;
    }
}
