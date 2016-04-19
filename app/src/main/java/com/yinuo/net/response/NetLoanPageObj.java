package com.yinuo.net.response;

import com.yinuo.mode.LoanGridViewModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.utils.NetConstant;
import com.yinuo.net.utils.NetParseUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/19.
 */
public class NetLoanPageObj extends NetBaseObject {

    private List<LoanGridViewModel> mOptions;

    @Override
    protected void parse(JSONObject obj) {
        mOptions = new ArrayList<LoanGridViewModel>();
        JSONArray array = NetParseUtils.getArray(NetConstant.NET_JSON_LOAN_FIELD_OPTIONS_LISTS, obj);
        parseArray(array);
    }

    private void parseArray(JSONArray array) {
        if (null != array) {
            int length = array.length();
            for (int i = 0; i < length; ++i) {
                try {
                    JSONObject obj = array.getJSONObject(i);
                    int itemId = NetParseUtils.getInt(NetConstant.NET_JSON_LOAN_OPTIONS_ID, obj);
                    String itemImg = NetParseUtils.getString(NetConstant.NET_JSON_LOAN_OPTIONS_IMG, obj);
                    String itemTxt = NetParseUtils.getString(NetConstant.NET_JSON_LOAN_OPTIONS_TXT, obj);
                    String itemHot = NetParseUtils.getString(NetConstant.NET_JSON_LOAN_OPTIONS_HOT, obj);
                    String itemLocation = NetParseUtils.getString(NetConstant.NET_JSON_LOAN_OPTIONS_LOCATION, obj);

                    LoanGridViewModel model = new LoanGridViewModel();
                    model.setItemId(itemId);
                    model.setItemImgUrl(itemImg);
                    model.setItemOption(itemTxt);
                    model.setItemHot(itemHot);
                    model.setItemLocation(itemLocation);

                    mOptions.add(model);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<LoanGridViewModel> getOptions() {
        return mOptions;
    }
}
