package com.zy.main.mvvm;

import android.view.View;

import com.zy.main.widget.RecyclerView;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

public class CommonViewHolder<T> extends RecyclerView.ViewHolder {
    protected ViewDataBinding viewDataBinding;
    protected int BR_id;

    public CommonViewHolder(@NonNull View itemView, int BR_id) {
        super(itemView);
        this.BR_id = BR_id;
    }

    public ViewDataBinding getViewDataBinding() {
        return viewDataBinding;
    }

    public void setViewDataBinding(ViewDataBinding viewDataBinding) {
        this.viewDataBinding = viewDataBinding;
    }

    public void setData(T t){
        viewDataBinding.setVariable(BR_id, t);
        //立即刷新，需要在主线程调用
        viewDataBinding.executePendingBindings();
    }

}
