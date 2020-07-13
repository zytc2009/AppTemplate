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

public class CommonRecyclerViewAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {
     private Context context;
     private LayoutInflater layoutInflater;
     private List<T> dataList;

     public CommonRecyclerViewAdapter(Context context){
         this.context = context;
         layoutInflater = LayoutInflater.from(context);
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

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {
        Log.d("RecyclerView", "whb onBindViewHolder()  position="+position);
        holder.setData(dataList.get(position));
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position, @NonNull List<Object> payloads) {
        if(payloads != null && payloads.size()>0){

        }else {
            onBindViewHolder(holder, position);
        }
    }

    @Override
    public synchronized int getItemCount() {
         if(dataList != null){
             return dataList.size();
         }
        return 0;
    }

    public synchronized void setDataList(List<T> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }
}
