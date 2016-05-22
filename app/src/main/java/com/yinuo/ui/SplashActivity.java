package com.yinuo.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.yinuo.Constants;
import com.yinuo.R;
import com.yinuo.base.BaseApplication;
import com.yinuo.base.WebActivity;
import com.yinuo.helper.ImageLoaderHelper;
import com.yinuo.helper.MapHelper;
import com.yinuo.listener.ILocation;
import com.yinuo.mode.WebModel;
import com.yinuo.net.utils.NetHelper;
import com.yinuo.utils.PreferenceUtils;
import com.yinuo.utils.ResUtils;
import com.yinuo.utils.StringUtils;

/**
 * Created by Administrator on 2016/5/5.
 */
public class SplashActivity extends Activity implements View.OnClickListener, ILocation {
    private ImageView mAdImg;
    private RelativeLayout mDefaultParent;
    private TextView mDefaultVersion;
    private RelativeLayout mAdLayoutParent;
    private TextView mAdSkip;
    private TextView mAdVersion;
    private boolean hasLoadedAdImg;
    private UiHandler mHandler = new UiHandler();
    private final int ENTER_AD_WEB = 0x001;
    private boolean isGotoMain = false;
    private boolean isClickAd = false;
    private MapHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_splash_layout);

        mHelper = new MapHelper(this);
        mHelper.locationEnable(true);
        mHelper.location();
        mHelper.setLocation(this);

        mAdImg = (ImageView) findViewById(R.id.app_splash_activity_img);
        mDefaultParent = (RelativeLayout) findViewById(R.id.app_splash_default_parent_layout);
        mDefaultVersion = (TextView) findViewById(R.id.app_splash_default_version);
        mAdLayoutParent = (RelativeLayout) findViewById(R.id.app_splash_ad_parent_layout);
        mAdSkip = (TextView) findViewById(R.id.app_splash_skip);
        mAdVersion = (TextView) findViewById(R.id.app_splash_ad_version);

        mAdImg.setOnClickListener(this);
        new AsyncWork().execute(PreferenceUtils.getInstance().getConfigAdImgUrl());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.app_splash_activity_img: {
                if (hasLoadedAdImg) {
                    isClickAd = true;
                    isGotoMain = true;
                    Intent intent = new Intent(this, WebActivity.class);
                    WebModel model = new WebModel();
                    model.setSupportCatch(false);
                    model.setUrl(PreferenceUtils.getInstance().getConfigAdEntranceUrl());
                    intent.putExtra(Constants.WEB_MODEL, model);
                    startActivityForResult(intent, ENTER_AD_WEB);
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ENTER_AD_WEB) {
            if (isGotoMain) {
                gotoMain();
            }
        }
    }

    private final class AsyncWork extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            if (!StringUtils.isEmpty(strings[0]) && NetHelper.getInstance(SplashActivity.this).isNetworkConnected()) {
                return ImageLoaderHelper.getInstance().loadBitmap(strings[0]);
            } else {
                countDown();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                countDown();
                hasLoadedAdImg = true;
                mAdImg.setImageBitmap(bitmap);
                hide(mDefaultParent);
                show(mAdSkip);
                // default show 10 for handle %1$s
                mAdSkip.setText(String.format(ResUtils.getString(SplashActivity.this, R.string.app_splash_skip_txt), 10));
                show(mAdLayoutParent);
            }
        }
    }

    private void countDown() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int time = 10;
                while (time > 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    time--;
                    Message msg = mHandler.obtainMessage();
                    msg.arg1 = time;
                    msg.what = mHandler.COUNT_DOWN;
                    msg.sendToTarget();
                }
                if (time <= 0 && isClickAd) {
                    isGotoMain = true;
                } else if (!isClickAd) {
                    gotoMain();
                }
            }
        }).start();
    }

    private final class UiHandler extends Handler {
        private final int COUNT_DOWN = 0x000;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case COUNT_DOWN: {
                    mAdSkip.setText(String.format(ResUtils.getString(SplashActivity.this, R.string.app_splash_skip_txt), msg.arg1));
                    break;
                }
            }
        }
    }

    private void show(View view) {
        view.setVisibility(View.VISIBLE);
    }

    private void hide(View view) {
        view.setVisibility(View.GONE);
    }

    private void gotoMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void locationSuccess(BDLocation location) {
        Log.e("ldx", "location success >> " + location.getCity() + " id " + location.getCityCode()
                + " adrStr " + location.getAddrStr() + " build Id == " + location.getBuildingID()
                + " build name " + location.getBuildingName() + " country : " + location.getCountry()
                + " district " + location.getDistrict() + " province " + location.getProvince());
        BaseApplication.getInstance().setBDLocation(location);
    }

    @Override
    public void locationFail() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
        mHelper.release();
    }
}
