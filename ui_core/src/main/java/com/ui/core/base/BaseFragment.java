package com.ui.core.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.ui.core.UICoreLog;
import com.ui.core.presenter.BasePresenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by shishoufeng on 2019/12/10.
 * <p>
 * desc : fragment 基类
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IBaseView{

    private static final String CLASS_TAG = "[UC] -> BaseFragment ";

    protected final String TAG = this.getClass().getSimpleName();

    private P mPresenter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        UICoreLog.i(CLASS_TAG," onAttach() : "+TAG);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UICoreLog.i(CLASS_TAG," onCreate() : "+TAG);

    }

    /**
     *
     * 子类实现Presenter,且必须继承BasePresenter
     *
     * @return P
     */
    protected abstract P createPresenter();





    @Override
    public void onStart() {
        super.onStart();
        UICoreLog.i(CLASS_TAG," onStart() : "+TAG);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UICoreLog.i(CLASS_TAG," onActivityResult() : "+TAG);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UICoreLog.i(CLASS_TAG," onViewCreated() : "+TAG);

    }

    @Override
    public void onResume() {
        super.onResume();
        UICoreLog.i(CLASS_TAG," onResume() : "+TAG);

    }

    @Override
    public void onPause() {
        super.onPause();
        UICoreLog.i(CLASS_TAG," onPause() : "+TAG);

    }

    @Override
    public void onStop() {
        super.onStop();
        UICoreLog.i(CLASS_TAG," onStop() : "+TAG);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        UICoreLog.i(CLASS_TAG," onDestroyView() : "+TAG);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UICoreLog.i(CLASS_TAG," onDestroy() : "+TAG);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        UICoreLog.i(CLASS_TAG," onDetach() : "+TAG);

    }


}
