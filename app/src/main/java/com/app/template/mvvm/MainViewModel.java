package com.app.template.mvvm;

import android.util.Log;
import android.widget.ImageView;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * java代码实现MVVM
 * @author whb
 */
public class MainViewModel extends ViewModel {
    //添加变量，我们主要是观察这个数据变化，来刷新页面
    private MutableLiveData<MainDataBean> mainData;
    private MutableLiveData<List<MainListItem>> mainList;

    private MainModel model;

    public MainViewModel(){
        mainData = new MutableLiveData<>();
        mainList = new MutableLiveData<>();
        model = new MainModel();
    }

    public MutableLiveData<MainDataBean> getMainData() {
        return mainData;
    }


    public MutableLiveData<List<MainListItem>> getMainList() {
        return mainList;
    }

    public void loadData(){
        mainData.postValue(model.loadData());
        mainList.postValue(model.getListData());
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }

    @BindingAdapter("bind:img")
    public static void setImage(ImageView view, String url){
        //显示图片
        Log.d("MainDataBean", "setImage() url="+url);
    }
}
