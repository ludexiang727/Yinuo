package com.yinuo.base;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.yinuo.R;
import com.yinuo.adapter.MainViewPagerAdapter;
import com.yinuo.net.request.NetRequest;
import com.yinuo.ui.CityChoosePageActivity;
import com.yinuo.ui.MineActivity;
import com.yinuo.utils.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/5.
 */
public class BaseParentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private final int MINE_REQUEST_CODE = 0X000;

    private FloatingActionButton mFloatActionButton;
    private DrawerLayout mDrawerLayout;
    private Resources mResources;

    protected TabLayout mTabLayout;
    protected ViewPager mViewPager;
    protected MainViewPagerAdapter mPagerAdapter;
    protected List<String> mTabTitle = new ArrayList<String>();
    private ImageView mHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main_layout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimary);
        }
        mResources = getResources();
        initialize();
        initViewPager();
        initTabLayout();

        // test default sign up
        NetRequest.getInstance().requestUserInfoBy(1, null);
    }

    private void initialize() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mFloatActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mTabLayout = (TabLayout) findViewById(android.R.id.tabs);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mFloatActionButton.setOnClickListener(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        mHeaderView = (ImageView) headerView.findViewById(R.id.app_mine_header);
        mHeaderView.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
    }

    private void initTabLayout() {
        String[] tab = mResources.getStringArray(R.array.main_view_pager_tab);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0; i < tab.length; ++i) {
            mTabTitle.add(tab[i]);
            mTabLayout.addTab(mTabLayout.newTab().setText(tab[i]));
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, CityChoosePageActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            // Handle the camera action
            case R.id.nav_gallery : {
                break;
            }
            case R.id.nav_slideshow: {
                break;
            }
            case R.id.nav_manage: {
                break;
            }
            case R.id.nav_share: {
                break;
            }
            case R.id.nav_send : {
                break;
            }
            default: {
                break;
            }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.fab: {
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            }
            case R.id.app_mine_header: {
                Intent intent = new Intent(this, MineActivity.class);
                startActivityForResult(intent, MINE_REQUEST_CODE);
                break;
            }
        }
    }
}
