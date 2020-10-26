package com.zy.main.kt_ui.model

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * 扩展自定义属性
 */
object BindingAdapters{
    @BindingAdapter("bindImage")
    @JvmStatic
    fun bindImage(view: View, image: String) {
//        view.visibility = if (image?.isEmpty()) View.GONE else View.VISIBLE
    }
}
