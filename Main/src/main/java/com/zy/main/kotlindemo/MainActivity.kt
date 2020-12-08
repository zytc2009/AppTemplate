package com.zy.kotlindemo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.zy.kotlindemo.coroutine.LaunchCoroutine
import com.zy.main.R
import kotlinx.android.synthetic.main.activity_main.*

//扩展
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_nobind)
        //直接访问元素
        tv_title.text = "Whb"
        LaunchCoroutine().displayDashboard(tv_title);
    }

    fun onText1Clicked(view: View){

    }

}