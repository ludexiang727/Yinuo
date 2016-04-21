package com.yinuo.base;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.yinuo.R;

/**
 * Created by ludexiang on 2016/4/20.
 */
public class WebActivity extends Activity {

    protected WebView mBaseWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_web_activity_main_layout);
    }
}
