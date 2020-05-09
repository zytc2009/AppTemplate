package com.ui.core.base;

import android.os.Bundle;

import com.ui.core.UICoreLog;
import com.ui.core.presenter.BasePresenter;
import com.ui.core.presenter.DefaultPresenter;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by shishoufeng on 2019/12/10.
 * <p>
 * desc :  抽象页面
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseView{

    private static final String CLASS_TAG = "[UC] -> BaseActivity ";

    protected final String TAG = this.getClass().getSimpleName();

    private P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResId());

        this.mPresenter = createPresenter();

        initView(savedInstanceState);

        initData();

    }



    /**
     * 获取layout resource id
     *
     * @return 布局layout
     */
    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     *
     * 子类实现Presenter,且必须继承BasePresenter
     *
     * @return P
     */
    protected abstract P createPresenter();


    /**
     * 初始化相关view
     *
     * @param savedInstanceState bundle 数据
     */
    protected abstract void initView(@Nullable Bundle savedInstanceState);


    /**
     * 初始化data
     */
    protected abstract void initData();



    @NonNull
    protected final P getPresenter() {
        if (this.mPresenter != null) {
            return this.mPresenter;
        }
        this.mPresenter = createPresenter();
        if (this.mPresenter == null) {
            UICoreLog.e(CLASS_TAG," --- used DefaultPresenter ---");
            //noinspection unchecked
            this.mPresenter = (P) new DefaultPresenter();
        }
        return this.mPresenter;
    }



    @Override
    protected void onStart() {
        super.onStart();
        UICoreLog.d(CLASS_TAG, "onStart(): "+TAG);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        UICoreLog.d(CLASS_TAG, "onReStart(): "+TAG);

    }

    @Override
    protected void onResume() {
        super.onResume();
        UICoreLog.d(CLASS_TAG, "onResume(): "+TAG);


    }

    @Override
    protected void onPause() {
        super.onPause();
        UICoreLog.d(CLASS_TAG, "onPause(): "+TAG);

    }

    @Override
    protected void onStop() {
        super.onStop();
        UICoreLog.d(CLASS_TAG, "onStop(): "+TAG);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UICoreLog.d(CLASS_TAG, "onDestroy(): "+TAG);


    }




}
