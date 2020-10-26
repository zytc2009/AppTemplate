package com.utils.simple.ui.compat

import com.arch.utils.ActivityUtils
import com.utils.simple.R

/**
 * Created by shishoufeng on 2020-02-07.
 * email:shishoufeng1227@126.com
 *
 *
 * ActivityUtils 工具类 相关测试
 *
 *
 *
 */
class CompatActivityUtilsActivity : BaseScrollTextCompatActivity(){


    override fun getActivityLabelTextId(): Int {
        return R.string.compat_activity_utils
    }

    override fun initData() {

        val sb = StringBuilder()

        sb.append(" isActivityDestroy : ")
                .append(ActivityUtils.isActivityDestroy(this))
                .append("\n")
                .append(" isActivityExists : ")
                .append(ActivityUtils.isActivityExists(this.packageName,"SimpleActivity"))


        materialTextView.text = sb.toString()

    }


}