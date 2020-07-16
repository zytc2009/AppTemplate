package com.app.template.kt_ui.model

import java.util.*

class MainModel {

    fun loadData():HomeDataBean{
        var homeDataBean:HomeDataBean = HomeDataBean();
        homeDataBean.title="标题栏";
        homeDataBean.desc ="这是内容";
        homeDataBean.image="http://pic1.win4000.com/wallpaper/1/5497cc99eab5a.jpg";

        return homeDataBean;
    }


    fun getListData(): List<HomeListItem>? {
        val dataList: MutableList<HomeListItem> = ArrayList()
        dataList.add(HomeListItem("1", "00001"))
        dataList.add(HomeListItem("2", "00002"))
        dataList.add(HomeListItem("3", "00003"))
        dataList.add(HomeListItem("4", "00004"))
        return dataList
    }
}