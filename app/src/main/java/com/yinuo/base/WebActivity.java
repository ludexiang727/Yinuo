package com.yinuo.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.yinuo.Constants;
import com.yinuo.R;
import com.yinuo.mode.WebModel;
import com.yinuo.utils.StringUtils;

/**
 * Created by ludexiang on 2016/4/20.
 */
public class WebActivity extends BaseActivity {
    protected WebView mBaseWebView;
    private final int RESULT_OK = 0;

    @Override
    protected int getContentLayout() {
        return R.layout.base_web_activity_main_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadView(View view) {
        mBaseWebView = (WebView) view.findViewById(R.id.base_web_activity_web_view);
        Intent intent = getIntent();
        WebModel model = (WebModel) intent.getSerializableExtra(Constants.WEB_MODEL);
        if (model == null || StringUtils.isEmpty(model.getUrl())) {
            throw new IllegalArgumentException("WebModel is null or WebModel's url is empty");
        }
        WebSettings settings = mBaseWebView.getSettings();
        settings.setSupportZoom(false);
        mBaseWebView.loadUrl(model.getUrl());
    }

    @Override
    protected void loadData() {
        dismissLoading();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        setResult(RESULT_OK);
    }
}
