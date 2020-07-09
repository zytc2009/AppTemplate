package com.app.template.mvvm;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

class CommonViewHolder<T> extends RecyclerView.ViewHolder {
    private ViewDataBinding viewDataBinding;
    private int BR_id;

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
