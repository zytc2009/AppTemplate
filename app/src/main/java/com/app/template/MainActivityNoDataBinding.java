package com.app.template;

import android.os.Bundle;
import android.widget.TextView;

import com.app.template.mvvm.MainDataBean;
import com.app.template.mvvm.MainViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MainActivityNoDataBinding extends AppCompatActivity {

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nobind);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getMainData().observe(this, new Observer<MainDataBean>() {
            @Override
            public void onChanged(MainDataBean mainDataBean) {
                ((TextView) findViewById(R.id.tv_title)).setText(mainDataBean.getTitle());
                ((TextView) findViewById(R.id.tv_desc)).setText(mainDataBean.getDesc());
            }
        });

        mainViewModel.loadData();
    }
}
