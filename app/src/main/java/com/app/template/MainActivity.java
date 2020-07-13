package com.app.template;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;

import com.app.template.databinding.ActivityMainBinding;
import com.app.template.mvvm.CommonRecyclerViewAdapter;
import com.app.template.mvvm.MainDataBean;
import com.app.template.mvvm.MainListItem;
import com.app.template.mvvm.MainViewModel;
import com.app.template.widget.LinearLayoutManager;
import com.http.test.TestModel;

import java.util.List;


/**
 * 使用databinding
 */
public class MainActivity extends AppCompatActivity implements Handler.Callback,View.OnClickListener{

    private MainViewModel mainViewModel;
    ActivityMainBinding activityMainBinding;
    private CommonRecyclerViewAdapter adapter;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        handler = new Handler(this);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        //观察主页面数据
        mainViewModel.getMainData().observe(this, new Observer<MainDataBean>() {
            @Override
            public void onChanged(MainDataBean mainDataBean) {
                //通知页面刷新
                activityMainBinding.setMainData(mainDataBean);
            }
        });
        //观察列表变化
        mainViewModel.getMainList().observe(this, new Observer<List<MainListItem>>() {
            @Override
            public void onChanged(List<MainListItem> mainListItems) {
                adapter.setDataList(mainListItems);
            }
        });

        //关联VM，这样才能关联点击事件
        activityMainBinding.setViewModel(mainViewModel);

        //recyclerView处理
        adapter  = new CommonRecyclerViewAdapter(this);
        activityMainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        activityMainBinding.recyclerView.setAdapter(adapter);


        activityMainBinding.viewStub.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                //如果没有在布局中设置数据传递，就需要执行这两行了。如果设置了，这段代码可以废弃
//                ViewDataBinding dataBinding = DataBindingUtil.bind(inflated);
//                dataBinding.setVariable(BR.mainData, mainViewModel.getMainData().getValue());
            }
        });

        //加载数据
        mainViewModel.loadData();
        //5秒后改变局部数据试试
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                 //测试属性刷新
//                mainViewModel.getMainData().getValue().setDesc("局部刷新");

                 //测试viewStub
//                if(!activityMainBinding.viewStub.isInflated()) {
//                    activityMainBinding.viewStub.getViewStub().inflate();
//                }

                //测试属性
//                activityMainBinding.checkbox.setChecked(true);

                //测试局部刷新
                Log.d("RecyclerView", "whb notifyItemChanged()  ");
                adapter.notifyItemChanged(3);

            }
        }, 5000);

        //测试Retrofit
//        new TestModel(this).getData();
    }


    @Override
    public boolean handleMessage(@NonNull Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {
//        Log.d("MainDataBean", "onClick() 22222");

    }

}
