package com.yinuo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.yinuo.base.BaseParentActivity;
import com.yinuo.ui.page.BossOnlinePageFragment;
import com.yinuo.ui.page.DiscoverPageFragment;
import com.yinuo.ui.page.HomePageFragment;
import com.yinuo.ui.page.InvestPageFragment;
import com.yinuo.ui.page.LoanPageFragment;
import com.yinuo.ui.page.MorePageFragment;
import com.yinuo.ui.page.PartnerPageFragment;
import com.yinuo.ui.page.WorkSpacePageFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseParentActivity {

    private List<Fragment> mPages = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** home page */
        HomePageFragment homePage = new HomePageFragment();
        /** discovery page -- 发现 */
        DiscoverPageFragment discoverPage = new DiscoverPageFragment();
        /** partner page -- 合伙人 */
        PartnerPageFragment partnerPage = new PartnerPageFragment();
        /** find invest page -- 找融资 */
        InvestPageFragment investPageFragment = new InvestPageFragment();
        /** small loan page -- 小额贷款*/
        LoanPageFragment loanPage = new LoanPageFragment();
        /** workspace page - 工作间*/
        WorkSpacePageFragment workSpacePageFragment = new WorkSpacePageFragment();
        /** boss online page */
        BossOnlinePageFragment bossPage = new BossOnlinePageFragment();
        /** more page */
        MorePageFragment morePage = new MorePageFragment();


        mPages.add(homePage);
        mPages.add(discoverPage);
        mPages.add(partnerPage);
        mPages.add(investPageFragment);
        mPages.add(loanPage);
        mPages.add(workSpacePageFragment);
        mPages.add(bossPage);
        mPages.add(morePage);

        mPagerAdapter.setFragments(mPages, mTabTitle);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(0, true);
    }

}
