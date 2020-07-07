package com.utils.simple.ui.compat

import android.widget.FrameLayout
import androidx.core.widget.NestedScrollView
import com.google.android.material.textview.MaterialTextView
import com.utils.simple.R

/**
 * Created by shishoufeng on 2020-02-09.
 * email:shishoufeng1227@126.com
 *
 * 抽象 滚动文本 页面
 *
 *
 */
abstract class BaseScrollTextCompatActivity : BaseCompatActivity(){

    lateinit var nestedScrollView: NestedScrollView
    lateinit var materialTextView : MaterialTextView

    final override fun getContentLayoutId(): Int {
        return R.layout.layout_scroll_text_view
    }

    override fun initView(flContent: FrameLayout) {
        nestedScrollView = flContent.findViewById(R.id.nested_scroll)
        materialTextView = flContent.findViewById(R.id.tv_scroll_content)


    }


}