package com.app.template.utils;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.arch.utils.ConvertUtils;


import androidx.annotation.RequiresApi;

public class InputMethodUtils {
    private static int mHeightOfKeyBoard;

    /**
     * open or close inputmethod,if already closed,it open.if opened,it close.
     *
     * @param view
     */
    public static void openInputMethod(View view) {
        if (view == null) {
            return;
        }
        try {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.showSoftInput(view, 0);
            imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        } catch (Exception ignore) {
        }
    }

    /**
     * close inputmethod
     *
     * @param view
     */
    public static void closeInputMethod(View view) {
        if (view == null) {
            return;
        }
        try {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (!imm.isActive()) {
                return;
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception ignore) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean checkKeyBoardOpen(View inputLayout) {
        return checkKeyBoardOpen(inputLayout, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean checkKeyBoardOpen(View inputLayout, OnKeyBoardOpenListener onKeyBoardOpenListener) {

        if (inputLayout == null || !inputLayout.isAttachedToWindow()) {
            return false;
        }
        View rootView = inputLayout.getRootView();
        if (rootView == null || !rootView.isAttachedToWindow()) {
            return false;
        }
        int heightRootView = rootView.getHeight();
        int heightKeyBoard = heightRootView - inputLayout.getHeight();

        // save state
        final int heightKeyBoardNow = heightKeyBoard - mHeightOfKeyBoard;
        int maxHeightInputmethod = (int) (heightRootView * 0.45f);
        int minHeightInputmethod = ConvertUtils.dp2px(100);
        boolean keyBoardOpened = heightKeyBoard > minHeightInputmethod;
//        boolean keyBoardOpened=InputMethodUtils.isKeyboardShowed(inputView);
//        S.s("RootView.height:" + heightRootView + ",InputMethod.height:max:" + maxHeightInputmethod + ",min:" + minHeightInputmethod + ",now:" + heightInputmethodNow);
        if (keyBoardOpened) {
            if (heightKeyBoardNow <= maxHeightInputmethod) {
                if (onKeyBoardOpenListener != null) {
                    onKeyBoardOpenListener.getKeyBoardHeight(heightKeyBoardNow);
                }
            }
        } else {
            mHeightOfKeyBoard = heightKeyBoard;
        }

        return keyBoardOpened;
    }

    public interface OnKeyBoardOpenListener {
        void getKeyBoardHeight(int heightOfKeyBoard);
    }
}
