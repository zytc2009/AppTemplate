package com.zy.main;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.webkit.MimeTypeMap;

import com.zy.main.databinding.ActivityMainBinding;
import com.zy.main.mvvm.CommonRecyclerViewAdapter;
import com.zy.main.mvvm.MainDataBean;
import com.zy.main.mvvm.MainListItem;
import com.zy.main.mvvm.MainRecyclerViewAdapter;
import com.zy.main.mvvm.MainViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


/**
 * MVVM实例
 * 使用databinding
 * kotlin版本见：HomeActivity
 * @deprecated 以后只使用kotlin编程了
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
        adapter  = new MainRecyclerViewAdapter(this);
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

        //测试Retrofit
//        new TestModel(this).getData();
    }


    @Override
    public boolean handleMessage(@NonNull Message msg) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }



    private void getAllPdf() {
        ContentResolver cr = getContentResolver();
        Uri uri = MediaStore.Files.getContentUri("external");
        String[] projection = null;
        String sortOrder = null; // unordered
        // only pdf
        String selectionMimeType = MediaStore.Files.FileColumns.MIME_TYPE + "=?";
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf");
        String[] selectionArgsPdf = new String[]{mimeType};
        Cursor cursor = cr.query(uri, projection, selectionMimeType, selectionArgsPdf, sortOrder);
        while (cursor.moveToNext()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String filePath = cursor.getString(column_index);//所有pdf文件路径
            Log.d("MainActivity", "getAllPdf() filePath=" + filePath);
        }
    }

}
