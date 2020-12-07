package com.zy.kotlindemo

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.zy.kotlindemo.coroutine.LaunchCoroutine
import kotlinx.android.synthetic.main.activity_main.*

//扩展
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //直接访问元素
        textView.text = "Whb"
        LaunchCoroutine().displayDashboard(textView);
    }

    fun onText1Clicked(view: View){

    }

}