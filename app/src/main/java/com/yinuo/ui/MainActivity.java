package com.yinuo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.yinuo.base.BaseParentActivity;
import com.yinuo.ui.page.BossPageFragment;
import com.yinuo.ui.page.DiscoverPageFragment;
import com.yinuo.ui.page.HomePageFragment;
import com.yinuo.ui.page.InvestPageFragment;
import com.yinuo.ui.page.MorePageFragment;
import com.yinuo.ui.page.NearbyPageFragment;
import com.yinuo.ui.page.StaffPageFragment;
import com.yinuo.ui.page.WorkSpacePageFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseParentActivity {

    private List<Fragment> mPages = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HomePageFragment homePage = new HomePageFragment();
        DiscoverPageFragment discoverPage = new DiscoverPageFragment();
        WorkSpacePageFragment workSpacePageFragment = new WorkSpacePageFragment();
        InvestPageFragment investPageFragment = new InvestPageFragment();
        BossPageFragment bossPage = new BossPageFragment();
        StaffPageFragment staffPage = new StaffPageFragment();
        NearbyPageFragment nearbyPage = new NearbyPageFragment();
        MorePageFragment morePage = new MorePageFragment();


        mPages.add(homePage);
        mPages.add(discoverPage);
        mPages.add(workSpacePageFragment);
        mPages.add(investPageFragment);
        mPages.add(bossPage);
        mPages.add(staffPage);
        mPages.add(nearbyPage);
        mPages.add(morePage);

        mPagerAdapter.setFragments(mPages, mTabTitle);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(0, true);
    }

}
