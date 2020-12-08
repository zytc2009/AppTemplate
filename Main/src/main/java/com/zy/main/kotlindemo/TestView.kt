package com.zy.kotlindemo

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

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
