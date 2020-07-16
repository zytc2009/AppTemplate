package com.app.template.kt_ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.template.R
import com.app.template.databinding.ActivityHomeBinding
import com.app.template.kt_ui.HomeRecyclerViewAdapter
import com.app.template.kt_ui.model.HomeDataBean
import com.app.template.kt_ui.model.HomeListItem
import com.app.template.kt_ui.model.MainViewModel
import com.app.template.mvvm.CommonRecyclerViewAdapter
import com.app.template.widget.LinearLayoutManager

/**
 * Kotlin实现MVVM
 *
 * Created by whb
 * JAVA版本见MainActivity
 */
class HomeActivity :AppCompatActivity(){
    private var adapter: HomeRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        var homeBinding:ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        val mainViewModel: MainViewModel = ViewModelProvider(this).get(MainViewModel::class.java);

        homeBinding.viewModel = mainViewModel;

        mainViewModel.mainData.observe(this, Observer<HomeDataBean> { it ->
            homeBinding.mainData = it;
        })

        //recyclerView处理
        adapter = HomeRecyclerViewAdapter(this)
        homeBinding.recyclerView.layoutManager = LinearLayoutManager(this);
        homeBinding.recyclerView.adapter = adapter;

        mainViewModel.mainList.observe(this, Observer {
            adapter!!.setDataList(it);
        })

        mainViewModel.loadData();

    }

}