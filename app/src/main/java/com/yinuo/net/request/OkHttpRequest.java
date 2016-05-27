package com.yinuo.net.request;

import android.util.Log;
import android.util.SparseArray;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.yinuo.base.BaseApplication;
import com.yinuo.net.IRequestListener;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.response.NetAppConfigObj;
import com.yinuo.net.response.NetBossOnlinePageObj;
import com.yinuo.net.response.NetDiscoveryPageObj;
import com.yinuo.net.response.NetHomePageObj;
import com.yinuo.net.response.NetInvestPageObj;
import com.yinuo.net.response.NetInvestWeChatObj;
import com.yinuo.net.response.NetLoanCalculatorObj;
import com.yinuo.net.response.NetLoanPageObj;
import com.yinuo.net.response.NetPartnerPageObj;
import com.yinuo.net.response.NetUserInfoObj;
import com.yinuo.net.response.NetWorkspacePageObj;
import com.yinuo.net.utils.NetConstant;
import com.yinuo.net.utils.NetHelper;
import com.yinuo.utils.AssetUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by ludexiang on 2016/4/8.
 */
public class OkHttpRequest <T extends NetBaseObject> implements Callback {
    private static OkHttpRequest sHttpRequest;
    private OkHttpClient mHttpClient;
    private IRequestListener<T> mListener;
    private T mObject;
    private SparseArray<Request> mRequestArray = new SparseArray<Request>();

    private OkHttpRequest() {
        mHttpClient = new OkHttpClient();
        mHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        mHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
    }

    public static OkHttpRequest getInstance() {
        return HttpRequestFactory.create();
    }

    private final static class HttpRequestFactory {
        public static OkHttpRequest create() {
            if (sHttpRequest == null) {
                sHttpRequest = new OkHttpRequest();
            }
            return sHttpRequest;
        }
    }

    public int httpGetRequest(String url, IRequestListener<T> listener, T t) {
        mObject = t;
        mListener = listener;
        int requestCode = mObject.hashCode();
        StringBuilder builder = new StringBuilder();
        String baseURL = builder.append(NetConstant.REQUEST_URL).append(url).toString();
        Request request = new Request.Builder().url(baseURL).build();
        Call call = mHttpClient.newCall(request);
        if (call.isExecuted()) {
            return requestCode;
        }

        mRequestArray.put(requestCode, request);
        call.enqueue(this);
        return  requestCode;
    }

    public int httpPostRequest(String url, RequestBody body, IRequestListener<T> listener, T t) {
        mObject = t;
        mListener = listener;
        int requestCode = mObject.hashCode();
        StringBuilder builder = new StringBuilder();
        String baseURL = builder.append(NetConstant.REQUEST_URL).append(url).toString();
        Request request = new Request.Builder().url(baseURL).post(body).build();

        Call call = mHttpClient.newCall(request);
        if (call.isExecuted()) {
            return requestCode;
        }

        mRequestArray.put(requestCode, request);
        call.enqueue(this);
        return requestCode;
    }


    @Override
    public void onFailure(Request request, IOException e) {
        if (!NetHelper.getInstance(BaseApplication.getInstance().getContext()).isNetworkConnected()) {
            mObject.parse(AssetUtils.readFile("networkerror.txt"));
            Log.d("ldx", "fuck ........mLIsener.." + mListener);
            if (mListener != null) {
                Log.i("ldx", "fuck ..........");
                mListener.onFail(mObject);
            }
        }

    }

    @Override
    public void onResponse(Response response) throws IOException {
        if (response.isSuccessful()) {
            Log.e("ldx", "onResponse ....... reuquest >>> ");
            ResponseBody body = response.body();
//            mObject.parse(body.string());
            // test begin
            if (mObject instanceof NetHomePageObj) {
                mObject.parse(AssetUtils.readFile("homedata.txt"));
            } else if (mObject instanceof NetDiscoveryPageObj) {
                mObject.parse(AssetUtils.readFile("discoverydata.txt"));
            } else if (mObject instanceof NetPartnerPageObj) {
                mObject.parse(AssetUtils.readFile("partnerdata.txt"));
            } else if (mObject instanceof NetInvestPageObj) {
                mObject.parse(AssetUtils.readFile("investdata.txt"));
            } else if (mObject instanceof NetLoanPageObj) {
                mObject.parse(AssetUtils.readFile("loanpagedata.txt"));
            } else if (mObject instanceof NetWorkspacePageObj) {
                mObject.parse(AssetUtils.readFile("workspacedata.txt"));
            } else if (mObject instanceof NetBossOnlinePageObj) {
                mObject.parse(AssetUtils.readFile("bossonline.txt"));
            } else if (mObject instanceof NetInvestWeChatObj) {
                mObject.parse(AssetUtils.readFile("investwechat.txt"));
            } else if (mObject instanceof NetAppConfigObj) {
                mObject.parse(AssetUtils.readFile("appconfig.txt"));
            } else if (mObject instanceof NetUserInfoObj) {
                mObject.parse(AssetUtils.readFile("userdata.txt"));
            } else if (mObject instanceof NetLoanCalculatorObj) {
                mObject.parse(AssetUtils.readFile("loan_calculator.txt"));
            }
            // ...
            else if (mObject instanceof NetBaseObject) {
                mObject.parse(AssetUtils.readFile("investwechatsendmsg.txt"));
            }
            // test end

            if (mListener != null) {
                mListener.onSuccess(mObject);
            }
        }
    }

    public void cancelRequest(int key) {
        Request request = mRequestArray.get(key);
        if (request != null) {
            mHttpClient.cancel(request);
        }
    }
}
