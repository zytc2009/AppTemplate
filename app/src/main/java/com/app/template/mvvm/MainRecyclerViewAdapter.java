package com.app.template.mvvm;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.app.template.BR;
import com.app.template.R;
import com.app.template.widget.RecyclerView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public class MainRecyclerViewAdapter<T> extends CommonRecyclerViewAdapter<MainListItem> {

     public MainRecyclerViewAdapter(Context context){
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
        ViewDataBinding dataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.main_list_view,  parent, false);
        CommonViewHolder holder = new CommonViewHolder(dataBinding.getRoot(), BR.itemData);
        //用于holder刷新
        holder.setViewDataBinding(dataBinding);
        return holder;
    }

}
