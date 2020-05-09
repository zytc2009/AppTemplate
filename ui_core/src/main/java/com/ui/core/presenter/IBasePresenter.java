package com.ui.core.presenter;


import com.ui.core.base.IBaseView;

/**
 * Created by shishoufeng on 2019/12/10.
 * <p>
 * desc : presenter 接口层
 */
public interface IBasePresenter<V extends IBaseView> {


    /**
     * 绑定View
     * 目前 支持activity和fragment
     *
     * @param view view
     */
    void onAttach(V view);

    /**
     * 做初始化的操作,需要在view的视图初始化完成之后才能调用
     * 建议只初始化一些对象,而不要去做耗时操作.
     */
    void onCreate();

    /**
     * 对应生命周期onStart方法
     */
    void onStart();

    /**
     * 对应生命周期onStart方法
     */
    void onRestart();

    /**
     * 对应生命周期onResume方法
     */
    void onResume();

    /**
     * 对应生命周期onPause方法
     */
    void onPause();

    /**
     * 对应生命周期onStop方法
     */
    void onStop();

    /**
     * 对应生命周期onDetach方法
     */
    void onDetach();


    /**
     * 获取当前绑定到presenter的对象
     *
     * @return V
     */
    V getView();


}
