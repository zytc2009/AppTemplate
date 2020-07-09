package com.app.template.mvvm;

import java.util.ArrayList;
import java.util.List;

public class MainModel {

    public MainDataBean loadData(){
        MainDataBean dataBean = new MainDataBean();
        dataBean.setTitle("标题栏");
        dataBean.setDesc("这是内容");
        dataBean.setImage("http://pic1.win4000.com/wallpaper/1/5497cc99eab5a.jpg");
        return dataBean;
    }

    public List<MainListItem> getListData(){
        List<MainListItem> dataList = new ArrayList<>();
        dataList.add(new MainListItem("1","00001"));
        dataList.add(new MainListItem("2","00002"));
        dataList.add(new MainListItem("3","00003"));
        dataList.add(new MainListItem("4","00004"));


        return  dataList;
    }
}
