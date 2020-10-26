package com.zy.main.kt_ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zy.main.R
import com.zy.main.databinding.ActivityHomeBinding
import com.zy.main.kt_ui.HomeRecyclerViewAdapter
import com.zy.main.kt_ui.fragment.CardSelectorFragment
import com.zy.main.kt_ui.model.HomeDataBean
import com.zy.main.kt_ui.model.MainViewModel
import com.zy.main.widget.LinearLayoutManager

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
//        setContentView(R.layout.activity_main_nobind)

//        supportFragmentManager.beginTransaction().add(R.id.activity_main,  CardSelectorFragment()).commit()

        var homeBinding: ActivityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        val mainViewModel: MainViewModel = ViewModelProvider(this).get(MainViewModel::class.java);
//
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
//
//        mainViewModel.loadData();

    }

}