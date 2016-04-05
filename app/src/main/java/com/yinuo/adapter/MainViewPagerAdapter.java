package com.yinuo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/5.
 * 主页Viewpager adapter
 */
public class MainViewPagerAdapter <T extends Fragment> extends FragmentPagerAdapter {
    private List<T> mViews = new ArrayList<T>();
    private List<String> mTabTitles = new ArrayList<String>();

    public MainViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    public void setFragments(List<T> fragments, List<String> tabTitle) {
        if (mViews.size() > 0) {
            mViews.clear();
        }

        if (fragments != null) {
            mViews.addAll(fragments);
        }

        if (mTabTitles.size() > 0) {
            mTabTitles.clear();
        }
        if (tabTitle != null) {
            mTabTitles.addAll(tabTitle);
        }
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == object;
//    }

    @Override
    public Fragment getItem(int position) {
        return mViews.get(position);
    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
//    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles.size() > 0 ? mTabTitles.get(position) : "";
    }
}
