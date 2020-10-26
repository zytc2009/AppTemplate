package com.zy.main.kt_ui.fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment
import com.zy.main.R
import kotlinx.android.synthetic.main.fragment_card_selector.*;
import kotlinx.android.synthetic.main.fragment_card_selector.view.*

public class CardSelectorFragment : Fragment() {
        val TAG = javaClass.canonicalName

        companion object {
                fun newInstance(): CardSelectorFragment {
                        return CardSelectorFragment()
                }
        }


        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
                var rootView = inflater?.inflate(R.layout.fragment_card_selector, container, false)
                rootView.btn_K.setOnClickListener { Log.d(TAG, "onViewCreated(): hello world"); }
                return rootView
        }

}
