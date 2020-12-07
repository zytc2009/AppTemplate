package com.zy.kotlindemo

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet

/**
 * 构造方法
 */
class TestView : AppCompatTextView {
    constructor(context: Context) : super(context) {
        println("constructor")
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

}
