package com.yinuo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.yinuo.base.BaseParentActivity;
import com.yinuo.ui.page.DiscoverPageFragment;
import com.yinuo.ui.page.HomePageFragment;
import com.yinuo.ui.page.MorePageFragment;
import com.yinuo.ui.page.NearbyPageFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseParentActivity {

    private List<Fragment> mPages = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HomePageFragment homePage = new HomePageFragment();
        DiscoverPageFragment discoverPage = new DiscoverPageFragment();
        NearbyPageFragment nearbyPage = new NearbyPageFragment();
        MorePageFragment morePage = new MorePageFragment();

        mPages.add(homePage);
        mPages.add(discoverPage);
        mPages.add(nearbyPage);
        mPages.add(morePage);

        mPagerAdapter.setFragments(mPages, mTabTitle);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(0, true);
    }

}
