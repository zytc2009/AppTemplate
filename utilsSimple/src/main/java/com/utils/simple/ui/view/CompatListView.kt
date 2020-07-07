package com.utils.simple.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arch.utils.ArrayUtils
import com.google.android.material.textview.MaterialTextView
import com.utils.simple.R

/**
 * Created by shishoufeng on 2020-02-09.
 * email:shishoufeng1227@126.com
 *
 *
 *
 *
 */
class CompatListView @JvmOverloads constructor(context : Context,attrs: AttributeSet? = null) : FrameLayout(context,attrs){

    private lateinit var recyclerView : RecyclerView

//    private lateinit var mCompatAdapter: CompatAdapter

    private val mCompatList = ArrayList<String>()

    init {

        initView()
    }

    fun initView() {

        val typedArray = context.obtainStyledAttributes(R.styleable.CompatListView)

        val mode = typedArray.getInt(R.styleable.CompatListView_layout_manager,1)

        typedArray.recycle()

        LayoutInflater.from(context).inflate(R.layout.layout_recycler_view,this,true)

        recyclerView = findViewById(R.id.recycler_list)

        recyclerView.layoutManager = if (mode == 1)  LinearLayoutManager(context) else GridLayoutManager(context,2)

//        mCompatAdapter = CompatAdapter()

//        recyclerView.adapter = mCompatAdapter



    }

    public fun setCompatListContent(list: List<String>) : Unit{
        if (ArrayUtils.isEmpty(list))
            return

//        mCompatAdapter.set

    }




//     internal class CompatAdapter : RecyclerView.Adapter<CompatViewHolder>(){
//
//         private lateinit var mList : List<String>
//
//         private lateinit var mContext: Context
//
//         constructor(context: Context){
//             this.mContext = context
//         }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompatViewHolder {
//            return CompatViewHolder(LayoutInflater.from())
//        }
//
//        override fun getItemCount(): Int {
//            return ArrayUtils.getSize(mList)
//        }
//
//        override fun onBindViewHolder(holder: CompatViewHolder, position: Int) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        }
//
//
//    }

    internal class CompatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvContext: MaterialTextView

        init {
            tvContext = itemView.findViewById(R.id.tv_content)
        }
    }


}