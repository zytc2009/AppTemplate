package com.utils.simple.ui.compat

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.utils.simple.R

/**
 * Created by shishoufeng on 2020/1/17.
 *
 * desc :
 *
 *
 */
abstract class BaseCompatActivity : AppCompatActivity() {

    lateinit var toolbar:Toolbar
    lateinit var flContent: FrameLayout

    lateinit var inflater: LayoutInflater

    final override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_compat)

        toolbar = findViewById(R.id.tool_bar)
        flContent = findViewById(R.id.fl_content)

        toolbar.title = getString(getActivityLabelTextId())

        inflater = layoutInflater

        inflater.inflate(getContentLayoutId(),flContent,true)

        toolbar.setNavigationOnClickListener{
            onClickBack()
        }

        initView(flContent)

        initData()
    }

    abstract fun getActivityLabelTextId(): Int

    abstract fun getContentLayoutId(): Int

    abstract fun initView(flContent: FrameLayout)

    abstract fun initData()

    fun onClickBack(){
        finish()
    }


}

