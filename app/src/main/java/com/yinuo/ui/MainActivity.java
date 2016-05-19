package com.yinuo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yinuo.base.BaseFragment;
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
    private FragmentManager mFragmentMgr;
    private FragmentTransaction mFragmentTransaction;
    private BaseFragment mCurrentFragment;

    private final int HOME_FRAGMENT = 0;
    private final int DISCOVER_FRAGMENT = 1;
    private final int PARTNER_FRAGMENT = 2;
    private final int INVEST_FRAGMENT = 3;
    private final int LOAN_FRAGMENT = 4;
    private final int WORKSPACE_FRAGMENT = 5;
    private final int BOSSONLINE_FRAGMENT = 6;
    private final int MORE_FRAGMENT = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** home page */
        HomePageFragment homePage = HomePageFragment.newInstance(HOME_FRAGMENT);
        /** discovery page -- 发现 */
        DiscoverPageFragment discoverPage = DiscoverPageFragment.newInstance(DISCOVER_FRAGMENT);
        /** partner page -- 合伙人 */
        PartnerPageFragment partnerPage = PartnerPageFragment.newInstance(PARTNER_FRAGMENT);
        /** find invest page -- 找融资 */
        InvestPageFragment investPageFragment = InvestPageFragment.newInstance(INVEST_FRAGMENT);
        /** small loan page -- 小额贷款*/
        LoanPageFragment loanPage = LoanPageFragment.newInstance(LOAN_FRAGMENT);
        /** workspace page - 工作间*/
        WorkSpacePageFragment workSpacePageFragment = WorkSpacePageFragment.newInstance(WORKSPACE_FRAGMENT);
        /** boss online page */
        BossOnlinePageFragment bossPage = BossOnlinePageFragment.newInstance(BOSSONLINE_FRAGMENT);
        /** more page */
        MorePageFragment morePage = MorePageFragment.newInstance(MORE_FRAGMENT);

        mPages.add(homePage);
        mPages.add(discoverPage);
        mPages.add(partnerPage);
        mPages.add(investPageFragment);
        mPages.add(loanPage);
        mPages.add(workSpacePageFragment);
        mPages.add(bossPage);
        mPages.add(morePage);

        // viewpager pre load data
        mViewPager.setOffscreenPageLimit(1);
        mPagerAdapter.setFragments(mPages, mTabTitle);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(0, true);

        mFragmentMgr = getSupportFragmentManager();
        mFragmentTransaction = mFragmentMgr.beginTransaction();
//        mFragmentTransaction.show(homePage).hide(discoverPage)
//                .hide(partnerPage).hide(investPageFragment)
//                .hide(loanPage).hide(workSpacePageFragment)
//                .hide(bossPage).hide(morePage).commitAllowingStateLoss();

        mCurrentFragment = homePage;
    }

    public BaseFragment getCurrentFragment() {
        return mCurrentFragment;
    }

    public void switchFragment(BaseFragment from, BaseFragment to) {
        if (mCurrentFragment != to) {
            mCurrentFragment = to;
            mFragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.slide_out_right);
            if (!to.isAdded()) {
                mFragmentTransaction.hide(from).add(to, "").commitAllowingStateLoss();
            } else {
                mFragmentTransaction.hide(from).show(to).commitAllowingStateLoss();
            }
        }
    }
}
