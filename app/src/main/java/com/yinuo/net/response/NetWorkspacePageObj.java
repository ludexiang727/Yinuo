package com.yinuo.net.response;

import com.yinuo.mode.WorkspaceOptionModel;
import com.yinuo.mode.WorkspacePageModel;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.net.utils.NetConstant;
import com.yinuo.net.utils.NetParseUtils;
import com.yinuo.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/4/20.
 */
public class NetWorkspacePageObj extends NetBaseObject {

    private List<WorkspacePageModel> mModels;
    private List<WorkspaceOptionModel> mOptions;

    @Override
    protected void parse(JSONObject obj) {
        mModels = new ArrayList<WorkspacePageModel>();
        mOptions = new ArrayList<WorkspaceOptionModel>();
        JSONArray optionArray = NetParseUtils.getArray(NetConstant.NET_JSON_WORKSPACE_FIELD_OPTIONS_LISTS, obj);
        parseOptionArray(optionArray);
        JSONArray array = NetParseUtils.getArray(NetConstant.NET_JSON_WORKSPACE_FIELD_LIST_LISTS, obj);
        parseArray(array);
    }

    private void parseOptionArray(JSONArray array) {
        if (null != array) {
            int length = array.length();
            for (int i = 0; i < length; ++i) {
                try {
                    JSONObject obj = array.getJSONObject(i);
                    int id = NetParseUtils.getInt(NetConstant.NET_JSON_WORKSPACE_OPTIONS_ID, obj);
                    String img = NetParseUtils.getString(NetConstant.NET_JSON_WORKSPACE_OPTIONS_IMG, obj);
                    String txt = NetParseUtils.getString(NetConstant.NET_JSON_WORKSPACE_OPTIONS_TXT, obj);

                    WorkspaceOptionModel optionModel = new WorkspaceOptionModel();
                    optionModel.setOptionId(id);
                    optionModel.setOptionImg(img);
                    optionModel.setOptionTxt(txt);
                    mOptions.add(optionModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void parseArray(JSONArray array) {
        if (array != null) {
            int length = array.length();
            for (int i = 0; i < length; ++i) {
                try {
                    JSONObject obj = array.getJSONObject(i);
                    int id = NetParseUtils.getInt(NetConstant.NET_JSON_WORKSPACE_LIST_ID, obj);
                    String img = NetParseUtils.getString(NetConstant.NET_JSON_WORKSPACE_LIST_IMG, obj);
                    String title = NetParseUtils.getString(NetConstant.NET_JSON_WORKSPACE_LIST_TITLE, obj);
                    String tag = NetParseUtils.getString(NetConstant.NET_JSON_WORKSPACE_LIST_TAGS, obj);
                    String location = NetParseUtils.getString(NetConstant.NET_JSON_WORKSPACE_LIST_LOCATION, obj);
                    String extra = NetParseUtils.getString(NetConstant.NET_JSON_WORKSPACE_LIST_EXTRA, obj);
                    String price = NetParseUtils.getString(NetConstant.NET_JSON_WORKSPACE_LIST_PRICE, obj);

                    String[] tags = StringUtils.split(tag, ",");
                    String[] extras = StringUtils.split(extra, ",");

                    WorkspacePageModel model = new WorkspacePageModel();
                    model.setWorkspaceId(id);
                    model.setWorkspaceImg(img);
                    model.setWorkspaceTitle(title);
                    model.setWorkspaceTags(tags);
                    model.setWorkspaceLocation(location);
                    model.setWorkspaceExtra(extras);
                    model.setWorkspacePrice(price);
                    mModels.add(model);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<WorkspaceOptionModel> getOptions() {
        return mOptions;
    }

    public List<WorkspacePageModel> getModels() {
        return mModels;
    }
}
