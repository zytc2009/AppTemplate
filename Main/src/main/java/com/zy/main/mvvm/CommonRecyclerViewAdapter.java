package com.zy.main.mvvm;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zy.main.widget.RecyclerView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class CommonRecyclerViewAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {
     protected Context context;
    protected LayoutInflater layoutInflater;
    protected List<T> dataList;

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
    public abstract CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {
        Log.d("RecyclerView", "whb onBindViewHolder()  position="+position +",data="+dataList.get(position));
        holder.setData(dataList.get(position));
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position, @NonNull List<Object> payloads) {

            onBindViewHolder(holder, position);

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
