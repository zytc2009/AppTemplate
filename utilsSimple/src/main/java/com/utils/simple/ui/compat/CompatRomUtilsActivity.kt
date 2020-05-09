package com.utils.simple.ui.compat

import android.widget.FrameLayout
import com.utils.simple.R

/**
 * Created by shishoufeng on 2020/1/16.
 *
 *
 * desc :
 */
class CompatRomUtilsActivity : BaseCompatActivity() {
    override fun getActivityLabelTextId(): Int {
        return R.string.compat_rom_utils
    }

    override fun getContentLayoutId(): Int {
        return R.layout.layout_scroll_text_view
    }

    override fun initView(flContent: FrameLayout) {
         //To change body of created functions use File | Settings | File Templates.
    }

    override fun initData() {
         //To change body of created functions use File | Settings | File Templates.
    }


}
