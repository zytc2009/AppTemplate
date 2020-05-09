package com.ui.core.presenter;

import android.content.Context;

import com.ui.core.UICoreLog;
import com.ui.core.base.IBaseView;
import com.ui.core.model.DefaultModel;
import com.ui.core.model.IBaseModel;

import java.lang.ref.SoftReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by shishoufeng on 2019/12/10.
 * <p>
 * desc :  抽象 presenter 层
 */
public abstract class BasePresenter<M extends IBaseModel, V extends IBaseView> implements IBasePresenter<V> {

    private static final String CLASS_TAG = "[UC] -> BasePresenter ";

    protected final String TAG = this.getClass().getSimpleName();

    /**
     * 当前的实体类.
     * Activity or fragment   接口类型的弱引用
     */
    private SoftReference<V> mViewRef;

    /**
     * 对应数据处理model
     */
    private M mModel;

    /**
     * 只要不是与UI相关的操作，请使用此Context。避免直接使用ActivityContext,造成页面无法销毁。
     */
    private Context mAppContext;

    /**view 是否已经销毁*/
    private boolean isViewDestroy = false;


    @Override
    public void onAttach(V view) {
        UICoreLog.i(CLASS_TAG, " onAttach() : "+TAG);

        this.mViewRef = new SoftReference<>(view);
        this.mModel = getModel();
    }


    @Override
    public void onCreate() {
        UICoreLog.i(CLASS_TAG, " onCreate() : "+TAG);

        if (getView() != null) {
            this.mAppContext = getView().getContext().getApplicationContext();
        }

    }


    @Override
    public void onStart() {
        UICoreLog.i(CLASS_TAG, " onStart() : "+TAG);


    }


    @Override
    public void onRestart() {
        UICoreLog.i(CLASS_TAG, " onRestart() : "+TAG);
    }


    @Override
    public void onResume() {
        UICoreLog.i(CLASS_TAG, " onResume() : "+TAG);

    }


    @Override
    public void onPause() {
        UICoreLog.i(CLASS_TAG, " onPause() : "+TAG);

    }


    @Override
    public void onStop() {
        UICoreLog.i(CLASS_TAG, " onStop() : "+TAG);


    }


    /**
     * 解除绑定
     */
    @Override
    public void onDetach() {
        UICoreLog.i(CLASS_TAG, " onDetach() : "+TAG);
        destroyView();

        this.mModel = null;

    }

    public boolean isViewDestroyed() {
        return this.isViewDestroy;
    }

    private void destroyView() {
        this.isViewDestroy = true;
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }


    @Nullable
    @Override
    public final V getView() {
        if (this.mViewRef != null) {
            return this.mViewRef.get();
        } else {
            return null;
        }
    }

    /**
     * 获取model
     *
     * @return M
     */
    @NonNull
    protected final M getModel() {
        if (this.mModel != null) {
            return this.mModel;
        }
        this.mModel = initModel();
        if (this.mModel == null) {
            UICoreLog.e(CLASS_TAG, " --- used DefaultModel ---");
            //noinspection unchecked
            this.mModel = (M) new DefaultModel();
        }
        return this.mModel;
    }

    /**
     * 支持model层
     *
     * @return 继承baseModel
     */
    protected abstract M initModel();

    /**
     * 只要不是与UI相关的操作，请使用此Context。避免直接使用Activity Context,造成页面无法销毁。
     *
     * @return Context
     */
    public Context getAppContext() {
        return mAppContext;
    }


}
