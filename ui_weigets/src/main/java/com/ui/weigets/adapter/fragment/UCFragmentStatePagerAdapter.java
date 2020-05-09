package com.ui.weigets.adapter.fragment;

import com.arch.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Created by shishoufeng on 2019/12/10.
 * <p>
 * desc :  通用 fragment 适配器
 */
public class UCFragmentStatePagerAdapter<F extends Fragment> extends FragmentStatePagerAdapter {


    private List<F> mFragments = null;
    private String[] mTitles;

    public UCFragmentStatePagerAdapter(@NonNull FragmentManager fm) {
        this(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    public UCFragmentStatePagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        mFragments = new ArrayList<>();
    }

    public void addFragment(F fragment) {
        if ((fragment != null) && (mFragments != null)) {
            mFragments.add(fragment);
            notifyDataSetChanged();
        }
    }

    public void setFragment(F fragment) {
        if ((fragment != null) && (mFragments != null)) {
            mFragments.clear();
            mFragments.add(fragment);
            notifyDataSetChanged();
        }
    }

    public void addFragments(ArrayList<F> fragments) {
        if ((mFragments != null) && (fragments != null)) {
            mFragments.addAll(fragments);
            notifyDataSetChanged();
        }
    }

    public void setFragments(ArrayList<F> fragments) {
        if ((mFragments != null) && (fragments != null)) {
            mFragments.clear();
            mFragments.addAll(fragments);
            notifyDataSetChanged();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null) {
            return mTitles[position];
        }
        return "";
    }


    @Override
    public F getItem(int index) {
        if ((index >= 0) && (index < getCount())) {
            return this.mFragments.get(index);
        }
        return null;
    }

    @Override
    public int getCount() {
        return ArrayUtils.getSize(mFragments);
    }


}
