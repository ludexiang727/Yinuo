package com.yinuo.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import com.yinuo.base.BaseApplication;
import com.yinuo.helper.DBHelper;
import com.yinuo.mode.AddressModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ludexiang on 2016/4/28.
 */
public class AssetUtils {

    private static DBHelper sHelper;

    public static String readFile(String fileName) {
        AssetManager manager = BaseApplication.getInstance().getContext().getAssets();
        InputStream is = null;
        try {
            is = manager.open(fileName);
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text = new String(buffer, "utf-8");
            return text;
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public static void parseProvince(Context context , String str) {
        sHelper = new DBHelper(context);

        try {
            JSONObject obj = new JSONObject(str);
            JSONArray array = obj.optJSONArray("province");
            for (int i = 0; i < array.length(); ++i) {
                AddressModel model = new AddressModel();
                JSONObject inner =  array.getJSONObject(i);
                int proId = inner.optInt("ProID");
                String proName = inner.optString("name");
                int proSort = inner.optInt("proSort");
                String mark = inner.optString("ProRemark");
                model.setProId(proId);
                model.setProvince(proName);
                model.setProSort(proSort);
                model.setProMark(mark);
                sHelper.insertValue2ProTable(model);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public static void parseCity(Context context , String str) {
        sHelper = new DBHelper(context);

        try {
            JSONObject obj = new JSONObject(str);
            JSONArray array = obj.optJSONArray("city_lists");
            for (int i = 0; i < array.length(); ++i) {
                AddressModel model = new AddressModel();
                JSONObject inner =  array.getJSONObject(i);
                int cityId = inner.optInt("CityID");
                String cityName = inner.optString("name");
                int proId = inner.optInt("ProID");
                model.setCityId(cityId);
                model.setProId(proId);
                if (cityName.contains("市")) {
                    model.setCity(cityName.substring(0, cityName.indexOf("市")));
                } else {
                    model.setCity(cityName);
                }
                if (inner.optInt("ishot") != 0) {
                    int isHot = inner.optInt("ishot");
                    model.setHotCity(isHot);
                }
                model.setCityPinYin(PingYinUtil.getPingYin(cityName));
                model.setCityFirstSpell(PingYinUtil.converterToFirstSpell(cityName));
                sHelper.insertValue2CityTable(model);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
