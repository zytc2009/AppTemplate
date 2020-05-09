package com.utils.simple.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arch.utils.ArrayUtils
import com.google.android.material.textview.MaterialTextView
import com.utils.simple.R
import com.utils.simple.bean.CompatBean

/**
 * Created by shishoufeng on 2020/1/16.
 *
 *
 * desc :
 */
class HomeCompatAdapter(private val mContext: Context) : RecyclerView.Adapter<HomeCompatAdapter.HomeCompatHolder>() {

    private var compatBeanList: List<CompatBean>? = null

    private val layoutInflater: LayoutInflater

    init {

        layoutInflater = LayoutInflater.from(mContext)
    }

    fun setCompatBeanList(compatBeanList: List<CompatBean>) {
        this.compatBeanList = compatBeanList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCompatHolder {
        return HomeCompatHolder(layoutInflater.inflate(R.layout.layout_text_view, parent, false))
    }

    override fun onBindViewHolder(holder: HomeCompatHolder, position: Int) {
        val compatBean = compatBeanList!![position] ?: return

        holder.textView.text = compatBean.compatName

        holder.itemView.setOnClickListener { v ->

            val intent = Intent(mContext, compatBean.clazz)
            mContext.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return ArrayUtils.getSize(compatBeanList)
    }

    class HomeCompatHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textView: MaterialTextView

        init {

            textView = itemView.findViewById(R.id.tv_content)
        }
    }
}
