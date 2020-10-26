package com.zy.main.kt_ui;

import android.content.Context;
import android.view.ViewGroup;

import com.zy.main.BR;
import com.zy.main.R;
import com.zy.main.kt_ui.model.HomeListItem;
import com.zy.main.mvvm.CommonRecyclerViewAdapter;
import com.zy.main.mvvm.CommonViewHolder;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public class HomeRecyclerViewAdapter extends CommonRecyclerViewAdapter<HomeListItem> {
     public HomeRecyclerViewAdapter(Context context){
         super(context);
     }

    /**
     * 这个方法一般是抽象方法
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //临时测试用的，实际项目中一般是抽象方法
        ViewDataBinding dataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.home_list_view,  parent, false);
        CommonViewHolder holder = new CommonViewHolder(dataBinding.getRoot(), BR.itemData);
        //用于holder刷新
        holder.setViewDataBinding(dataBinding);
        return holder;
    }

}
