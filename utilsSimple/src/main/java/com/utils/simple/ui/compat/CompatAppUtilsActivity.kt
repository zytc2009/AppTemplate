package com.utils.simple.ui.compat

import android.widget.FrameLayout
import androidx.core.widget.NestedScrollView
import com.arch.utils.AppUtils
import com.google.android.material.textview.MaterialTextView
import com.utils.simple.R

/**
 * Created by shishoufeng on 2020/1/17.
 *
 * desc :
 *
 *
 */
class CompatAppUtilsActivity : BaseCompatActivity() {

    lateinit var nestedScrollView: NestedScrollView
    lateinit var materialTextView: MaterialTextView

    override fun getActivityLabelTextId(): Int {
        return R.string.compat_app_utils
    }

    override fun getContentLayoutId(): Int {
        return R.layout.layout_scroll_text_view
    }

    override fun initView(flContent: FrameLayout) {

        nestedScrollView = flContent.findViewById(R.id.nested_scroll)
        materialTextView = flContent.findViewById(R.id.tv_scroll_content)

    }

    override fun initData() {

        val sb = StringBuilder()

        sb.append("AppVersionCode : ")
                .append(AppUtils.getAppVersionCode(this))
                .append("\n")
                .append("AppVersionName : ")
                .append(AppUtils.getAppVersionName(this))


        materialTextView.text = sb.toString()

    }


}