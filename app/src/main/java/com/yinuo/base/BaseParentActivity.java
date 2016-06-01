package com.yinuo.base;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.adapter.EventBusAdapter;
import com.yinuo.adapter.MainViewPagerAdapter;
import com.yinuo.helper.ImageLoaderHelper;
import com.yinuo.mode.UserModel;
import com.yinuo.net.request.NetRequest;
import com.yinuo.ui.CityChoosePageActivity;
import com.yinuo.ui.LoginActivity;
import com.yinuo.ui.MineActivity;
import com.yinuo.ui.SettingsActivity;
import com.yinuo.utils.BitmapUtils;
import com.yinuo.utils.MessageEventUtil;
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
    private LinearLayout mNavigationView;
    private ImageView mHeaderView;
    private TextView mUserName;
    private TextView mUserAccount;
    private LoginEvent mLoginEvent = new LoginEvent();

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
        MessageEventUtil.getInstance().setEventAdapter(mLoginEvent);
        MessageEventUtil.getInstance().register();
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
        mNavigationView = (LinearLayout) headerView.findViewById(R.id.app_navigation_layout);
        mHeaderView = (ImageView) headerView.findViewById(R.id.app_mine_header);
        mUserName = (TextView) headerView.findViewById(R.id.app_mine_name);
        mUserAccount = (TextView) headerView.findViewById(R.id.app_mine_account);
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
            case R.id.nav_camera: {
                break;
            }
            // Handle the camera action
            case R.id.nav_gallery : {
                break;
            }
            case R.id.nav_slideshow: {
                break;
            }
            case R.id.nav_manage: {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
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
                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, MINE_REQUEST_CODE);
                break;
            }
        }
    }

    private final class LoginEvent extends EventBusAdapter {

        @Override
        public void onEventMainThread(EventBusAdapter event) {
            UserModel userInfo = BaseApplication.getInstance().getUserModel();
            Bitmap bitmap = ImageLoaderHelper.getInstance().loadBitmap(userInfo.getUserHeader());
            mUserName.setText(userInfo.getUserNickName());
            mUserAccount.setText(userInfo.getUserAccount());
            if (bitmap != null) {
                Bitmap blurBit = BitmapUtils.doBlur(bitmap, 2, true);
                mNavigationView.setBackgroundDrawable(BitmapUtils.bitmapToDrawable(blurBit));
            } else {
                mNavigationView.setBackgroundResource(R.drawable.side_nav_bar);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MessageEventUtil.getInstance().unRegister();
    }
}
