package com.yinuo.ui.sub;

import android.os.Bundle;
import android.view.Window;

import com.yinuo.R;
import com.yinuo.base.BaseActivity;

/**
 * Created by Administrator on 2016/4/9.
 */
public class HomePageDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_page_details_layout);
    }
}
