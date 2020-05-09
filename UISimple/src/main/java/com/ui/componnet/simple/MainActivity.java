package com.ui.componnet.simple;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TableLayout;

import com.arch.UtilsLog;
import com.ui.core.UICoreLog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.vp_simple_main)
    ViewPager vpSimpleMain;

    @BindView(R.id.tab_main)
    TableLayout tabMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LogDelegate logDelegate = new LogDelegate();

        UICoreLog.setDebug(BuildConfig.DEBUG);
        UICoreLog.setDelegate(logDelegate);

        UtilsLog.setDebug(BuildConfig.DEBUG);
        UtilsLog.setDelegate(logDelegate);



        new Handler().postDelayed(() -> ComUtility.RootCommand(""),4 * 1000);




    }




}
