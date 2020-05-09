package com.utils.simple.ui.compat

import android.widget.FrameLayout
import com.utils.simple.R

/**
 * Created by shishoufeng on 2020/1/17.
 *
 * desc :
 *
 *
 */
class CompatArrayUtilsActivity:BaseCompatActivity(){

    override fun getActivityLabelTextId(): Int {
        return R.string.compat_array_utils
    }

    override fun getContentLayoutId(): Int {
        return R.layout.layout_scroll_text_view
    }

    override fun initView(flContent: FrameLayout) {
    }

    override fun initData() {
    }

}