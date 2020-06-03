package com.utils.simple.ui.compat

import android.widget.FrameLayout
import androidx.core.widget.NestedScrollView
import com.arch.utils.AppUtils
import com.google.android.material.textview.MaterialTextView
import com.utils.simple.Constants
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
                .append("\n")
                //UI 组件项目是否安装
                .append("weight APP 是否已安装 : ")
                .append(AppUtils.isAppInstalled(this,Constants.WEIGHT_PACKAGE_NAME))
                .append("\n")
                // 判断 是否可调试
                .append("当前APP 是否可调式 : ")
                .append(AppUtils.isAppDebug())
                .append("\n")
                // 是否已安装
                .append("weight APP 是否已安装 : ")
                .append(AppUtils.isAppDebug(Constants.WEIGHT_PACKAGE_NAME))
                .append("\n")
                // 活动
                .append("应用是否在前台 : ")
                .append(AppUtils.isAppForeground())
                .append("\n")
                .append("weight APP 是否在前台 : ")
                .append(AppUtils.isAppForeground(Constants.WEIGHT_PACKAGE_NAME))
                .append("\n")
                // running
                .append("weight APP 是否在运行 : ")
                .append(AppUtils.isAppRunning(Constants.WEIGHT_PACKAGE_NAME))



        materialTextView.text = sb.toString()

    }


}