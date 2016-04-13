package com.yinuo.ui.sub;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.base.BaseActivity;
import com.yinuo.mode.HomeItemDetailBannerMode;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.request.NetRequest;
import com.yinuo.net.response.NetHomePageDetailsObj;
import com.yinuo.ui.component.widget.view.FlipperViewGroup;
import com.yinuo.ui.component.widget.view.FloatingOptionView;
import com.yinuo.ui.component.widget.view.IconRoundCornerProgressBar;
import com.yinuo.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/9.
 */
public class HomePageDetailsActivity extends BaseActivity implements View.OnClickListener {

    private final int UPDATE_UI = 0x000;
    private FlipperViewGroup mDetailsFlipperViewGroup;
    private int mAppID;
    private List<HomeItemDetailBannerMode> mItemBanners = new ArrayList<HomeItemDetailBannerMode>();
    private FloatingActionButton mFloatActionButton;
    private IconRoundCornerProgressBar mProgressBar;
    private Button mDownload;
    private TextView mDownloadPercent;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_UI: {
                    mDetailsFlipperViewGroup.setFlipperView(mItemBanners);
                    break;
                }
            }
        }
    };

    @Override
    protected int getContentLayout() {
        return R.layout.home_page_details_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void loadView(final View view) {
        mFloatActionButton = (FloatingActionButton) view.findViewById(R.id.home_page_detail_floating_action_button);
        mDetailsFlipperViewGroup = (FlipperViewGroup) view.findViewById(R.id.home_page_detail_flipper_viewgroup);
        mProgressBar = (IconRoundCornerProgressBar) view.findViewById(R.id.home_page_detail_progress);
        mDownload = (Button) view.findViewById(R.id.home_page_detail_download);
        mDownloadPercent = (TextView) view.findViewById(R.id.home_page_detail_percent);
        mDownload.setOnClickListener(this);
        mFloatActionButton.setOnClickListener(this);
    }

    @Override
    public void loadData() {
        NetRequest.getInstance().requestAppDetails(mAppID, this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_page_detail_download: {
                mProgressBar.setProgressBackgroundColor(R.color.custom_progress_background);
                test();
                break;
            }
            case R.id.home_page_detail_floating_action_button: {
                FloatingOptionView optionView = new FloatingOptionView(this);
                optionView.setFloatActionLocation(AppUtils.viewOnLocation(mFloatActionButton));
                break;
            }
        }
    }

    /// test
    int progress = 0;
    private void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = true;
                while(flag) {
                    while (progress <= 100) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        progress++;
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mProgressBar.setProgress(progress);

                            }
                        });
                        if (progress >= 100) {
                            flag = false;
                        }
                    }

                }
            }
        }).start();
    }

    @Override
    public void onSuccess(NetBaseObject object) {
        super.onSuccess(object);
        if (object instanceof NetHomePageDetailsObj) {
            NetHomePageDetailsObj detailsObj = (NetHomePageDetailsObj) object;
            List<HomeItemDetailBannerMode> images = detailsObj.getDetailsImgs();
            if (images != null && !images.isEmpty()){
                mItemBanners.addAll(images);
            }
            mHandler.sendEmptyMessage(UPDATE_UI);
        }
    }

    @Override
    public void onFail(NetBaseObject object) {
        super.onFail(object);
    }


}
