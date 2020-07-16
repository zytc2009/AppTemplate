package com.app.template.kt_ui.model

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.template.mvvm.MainListItem

class MainViewModel: ViewModel() {
    val mainData: MutableLiveData<HomeDataBean> = MutableLiveData()
    val mainList: MutableLiveData<List<HomeListItem>> = MutableLiveData()
    val model: MainModel = MainModel()

    fun loadData(){
        val data:HomeDataBean = model.loadData();
        mainData.postValue(data);

        val datalist: List<HomeListItem>? = model.getListData();
        mainList.postValue(datalist);
    }


    //自定义处理
    fun onClick(view: View?, data: String) {
        Log.d("MainDataBean", "onClick() data=$data")
    }

}