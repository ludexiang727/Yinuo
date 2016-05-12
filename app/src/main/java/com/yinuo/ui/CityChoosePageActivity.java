package com.yinuo.ui;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.yinuo.R;
import com.yinuo.base.BaseActivity;
import com.yinuo.helper.DBHelper;
import com.yinuo.mode.AddressModel;
import com.yinuo.ui.component.widget.view.CityChoosePageListView;
import com.yinuo.ui.component.widget.view.LetterListView;
import com.yinuo.utils.PingYinUtil;
import com.yinuo.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ludexiang on 2016/5/10.
 */
public class CityChoosePageActivity extends BaseActivity implements AbsListView.OnScrollListener, Comparator<AddressModel> {
    private ResultListAdapter resultListAdapter;
    private CityChoosePageListView mCityPageListView;
    private ListView resultList;
    private TextView overlay; // 对话框首字母textview
    private LetterListView mLetterListView; // A-Z listview
    private HashMap<String, Integer> mAlphaIndexMap;// 存放存在的汉语拼音首字母和与之对应的列表位置
    private String[] mSections;// 存放存在的汉语拼音首字母
    private OverlayThread overlayThread; // 显示首字母对话框
    private EditText mCityEditView;
    private TextView tv_noresult;

    private UIHandler mHandler = new UIHandler();

    private DBHelper mDBHelper;
    private String[] mAllCityDefault;
    private ArrayList<AddressModel> mAllCityList; // 所有城市列表
    private ArrayList<AddressModel> mCityHotLists;
    private ArrayList<AddressModel> mCityResultLists;
    private ArrayList<String> mCityHistory;
    private String currentAddressModel; // 用于保存定位到的城市
    private int locateProcess = 1; // 记录当前定位的状态 正在定位-定位成功-定位失败
    private boolean isNeedFresh;

    @Override
    protected int getContentLayout() {
        return R.layout.city_choose_page_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showTitle(false);
    }

    @Override
    protected void loadView(View view) {
        mDBHelper = new DBHelper(this);
        mCityPageListView = (CityChoosePageListView) view.findViewById(R.id.city_choose_page_list_view);
        resultList = (ListView) view.findViewById(R.id.city_choose_search_result_list_view);
        mCityEditView = (EditText) view.findViewById(R.id.city_choose_search);
        tv_noresult = (TextView) view.findViewById(R.id.city_choose_page_search_no_result);
        mCityEditView.addTextChangedListener(new EditWatcher());
        mLetterListView = (LetterListView) findViewById(R.id.city_choose_page_letter_view);
        mLetterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());

        mAllCityList = new ArrayList<AddressModel>();
        mCityHotLists = new ArrayList<AddressModel>();
        mCityResultLists = new ArrayList<AddressModel>();
        mCityHistory = new ArrayList<String>();
        mAlphaIndexMap = new HashMap<String, Integer>();

        overlayThread = new OverlayThread();
        isNeedFresh = true;
        mCityPageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position >= 4) {

//                    Toast.makeText(getApplicationContext(),
//                            mAllCityList.get(position).getName(),
//                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        locateProcess = 1;
        
        mCityPageListView.setList(mAllCityList);
        
        mCityPageListView.setOnScrollListener(this);
        resultListAdapter = new ResultListAdapter(this, mCityResultLists);
        resultList.setAdapter(resultListAdapter);
        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                /*Toast.makeText(getApplicationContext(),
                        mCityResultLists.get(position).getName(), Toast.LENGTH_SHORT)
                        .show();*/
            }
        });
        initOverlay();
        cityInit();
        hisAddressModelInit();

        InitLocation();
    }

    @Override
    protected void loadData() {
        dismissLoading();
        // all city
        List<AddressModel> allCity = mDBHelper.getAllCity();
        Message msg = mHandler.obtainMessage();
        msg.obj = allCity;
        msg.what = mHandler.NOTIFY_ALL_CITY_CHANGED;
        msg.sendToTarget();
        // hot city
        List<AddressModel> hotCity = mDBHelper.getHotCity();
        msg = mHandler.obtainMessage();
        msg.obj = hotCity;
        msg.what = mHandler.NOTIFY_HOT_CITY_CHANGED;
        msg.sendToTarget();
        // recent city
        List<AddressModel> recentCity = mDBHelper.getRecentCity();
        msg = mHandler.obtainMessage();
        msg.what = mHandler.NOTIFY_RECCENT_CITY_CHANGED;
        msg.obj = recentCity;
        msg.sendToTarget();
    }

    private final class EditWatcher implements TextWatcher {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString() == null || "".equals(s.toString())) {
                mLetterListView.setVisibility(View.VISIBLE);
                mCityPageListView.setVisibility(View.VISIBLE);
                resultList.setVisibility(View.GONE);
                tv_noresult.setVisibility(View.GONE);
            } else {
                mCityResultLists.clear();
                mLetterListView.setVisibility(View.GONE);
                mCityPageListView.setVisibility(View.GONE);
                getResultAddressModelList(s.toString());
                if (mCityResultLists.size() <= 0) {
                    tv_noresult.setVisibility(View.VISIBLE);
                    resultList.setVisibility(View.GONE);
                } else {
                    tv_noresult.setVisibility(View.GONE);
                    resultList.setVisibility(View.VISIBLE);
                    resultListAdapter.notifyDataSetChanged();
                }
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private final class UIHandler extends Handler {
        private final int NOTIFY_ALL_CITY_CHANGED = 0x000;
        private final int NOTIFY_HOT_CITY_CHANGED = 0x001;
        private final int NOTIFY_RECCENT_CITY_CHANGED = 0x002;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NOTIFY_ALL_CITY_CHANGED: {
                    List<AddressModel> allCity = (List<AddressModel>) msg.obj;
                    if (allCity != null) {
                        mAllCityList.addAll(allCity);
                        Collections.sort(mAllCityList, CityChoosePageActivity.this);
                    }
                    saveSections();
                    mCityPageListView.getCityChooseAdapter().notifyDataSetChanged();
                    break;
                }
                case NOTIFY_HOT_CITY_CHANGED: {
                    List<AddressModel> hotCity = (List<AddressModel>) msg.obj;
                    if (hotCity != null) {
                        mCityHotLists.addAll(hotCity);
                    }
                    mCityPageListView.setHotList(mCityHotLists);
                    break;
                }
                case NOTIFY_RECCENT_CITY_CHANGED: {
                    List<AddressModel> recentCity = (List<AddressModel>) msg.obj;
                    if (recentCity != null) {
                        mCityResultLists.addAll(recentCity);
                    }
                    mCityPageListView.setRecentList(mCityResultLists);
                }
            }
        }
    }

    private void saveSections() {
        mSections = new String[mAllCityList.size()];
        for (int i = 0; i < mAllCityList.size(); i++) {
            // 当前汉语拼音首字母
            String currentStr = StringUtils.getAlpha(this, mAllCityList.get(i).getCityPinYin());
            // 上一个汉语拼音首字母，如果不存在为" "
            String previewStr = (i - 1) >= 0 ? StringUtils.getAlpha(this, mAllCityList.get(i - 1).getCityPinYin()) : " ";
            if (!previewStr.equals(currentStr)) {
                String name = StringUtils.getAlpha(this, mAllCityList.get(i).getCityPinYin());
                mAlphaIndexMap.put(name, i);
                mSections[i] = name;
            }
        }
    }

    public void InsertAddressModel(String name) {
//        SQLiteDatabase db = helper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("select * from recentcity where name = '"
//                + name + "'", null);
//        if (cursor.getCount() > 0) { //
//            db.delete("recentcity", "name = ?", new String[] { name });
//        }
//        db.execSQL("insert into recentcity(name, date) values('" + name + "', "
//                + System.currentTimeMillis() + ")");
//        db.close();
    }

    private void InitLocation() {
        // 设置定位参数
//        LocationClientOption option = new LocationClientOption();
//        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(10000); // 10分钟扫描1次
//        // 需要地址信息，设置为其他任何值（string类型，且不能为null）时，都表示无地址信息。
//        option.setAddrType("all");
//        // 设置是否返回POI的电话和地址等详细信息。默认值为false，即不返回POI的电话和地址信息。
//        option.setPoiExtraInfo(true);
//        // 设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
//        option.setProdName("通过GPS定位我当前的位置");
//        // 禁用启用缓存定位数据
//        option.disableCache(true);
//        // 设置最多可返回的POI个数，默认值为3。由于POI查询比较耗费流量，设置最多返回的POI个数，以便节省流量。
//        option.setPoiNumber(3);
//        // 设置定位方式的优先级。
//        // 当gps可用，而且获取了定位结果时，不再发起网络请求，直接返回给用户坐标。这个选项适合希望得到准确坐标位置的用户。如果gps不可用，再发起网络请求，进行定位。
//        option.setPriority(LocationClientOption.GpsFirst);
//        mLocationClient.setLocOption(option);
    }

    private void cityInit() {
        mAllCityDefault = getResources().getStringArray(R.array.city_choose_page_letter);
        List<AddressModel> virtual = new ArrayList<AddressModel>();
        AddressModel city = new AddressModel(); // 当前定位城市
        city.setCityName(mAllCityDefault[0]);
        city.setCityPinYin("0");
        virtual.add(city);
        city = new AddressModel(); // 最近访问的城市
        city.setCityName(mAllCityDefault[1]);
        city.setCityPinYin("1");
        virtual.add(city);
        city = new AddressModel(); // 热门城市
        city.setCityName(mAllCityDefault[2]);
        city.setCityPinYin("2");
        virtual.add(city);
        city = new AddressModel(); // 全部城市
        city.setCityName(mAllCityDefault[3]);
        city.setCityPinYin("3");
        virtual.add(city);
        mAllCityList.addAll(virtual);
    }

    private void hisAddressModelInit() {
//        SQLiteDatabase db = helper.getReadableDatabase();
//        Cursor cursor = db.rawQuery(
//                "select * from recentcity order by date desc limit 0, 3", null);
//        while (cursor.moveToNext()) {
//            mCityHistory.add(cursor.getString(1));
//        }
//        cursor.close();
//        db.close();
    }

    private void getResultAddressModelList(String keyword) {
        List<AddressModel> searchResult = mDBHelper.getSearchCityBy(keyword);
        if (searchResult != null) {
            mCityResultLists.addAll(searchResult);
        }
        Collections.sort(mCityResultLists, this);
    }

    /** a-z排序 */
    @Override
    public int compare(AddressModel lhs, AddressModel rhs) {
        String a = lhs.getCityPinYin().substring(0, 1);
        String b = rhs.getCityPinYin().substring(0, 1);
        int flag = a.compareTo(b);
        if (flag == 0) {
            return a.compareTo(b);
        } else {
            return flag;
        }
    }

    /**
     * 实现实位回调监听
     */
//    public class MyLocationListener implements BDLocationListener {
//
//        @Override
//        public void onReceiveLocation(BDLocation arg0) {
//            Log.e("info", "city = " + arg0.getAddressModel());
//            if (!isNeedFresh) {
//                return;
//            }
//            isNeedFresh = false;
//            if (arg0.getAddressModel() == null) {
//                locateProcess = 3; // 定位失败
//                mCityPageListView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//                return;
//            }
//            currentAddressModel = arg0.getAddressModel().substring(0,
//                    arg0.getAddressModel().length() - 1);
//            locateProcess = 2; // 定位成功
//            mCityPageListView.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//        }
//
//        @Override
//        public void onReceivePoi(BDLocation arg0) {
//
//        }
//    }

    private class ResultListAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<AddressModel> results = new ArrayList<AddressModel>();

        public ResultListAdapter(Context context, ArrayList<AddressModel> results) {
            inflater = LayoutInflater.from(context);
            this.results = results;
        }

        @Override
        public int getCount() {
            return results.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.city_choose_page_alpha_city_layout, null);
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) convertView
                        .findViewById(R.id.city_choose_page_city_name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.name.setText(results.get(position).getCityName());
            return convertView;
        }

        class ViewHolder {
            TextView name;
        }
    }

    private boolean mReady;

    // 初始化汉语拼音首字母弹出提示框
    private void initOverlay() {
        mReady = true;
        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.city_choose_page_overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }

    private boolean isScroll = false;

    private class LetterListViewListener implements LetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            isScroll = false;
            if (mAlphaIndexMap.get(s) != null) {
                int position = mAlphaIndexMap.get(s);
                mCityPageListView.setSelection(position);
                overlay.setText(s);
                overlay.setVisibility(View.VISIBLE);
                mHandler.removeCallbacks(overlayThread);
                // 延迟一秒后执行，让overlay为不可见
                mHandler.postDelayed(overlayThread, 1000);
            }
        }
    }

    // 设置overlay不可见
    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_TOUCH_SCROLL
                || scrollState == SCROLL_STATE_FLING) {
            isScroll = true;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (!isScroll) {
            return;
        }

        if (mReady) {
            String text;
            String name = mAllCityList.get(firstVisibleItem).getCityName();
            String pinyin = mAllCityList.get(firstVisibleItem).getCityPinYin();
            if (firstVisibleItem < 4) {
                text = name;
            } else {
                text = PingYinUtil.converterToFirstSpell(pinyin).substring(0, 1).toUpperCase();
            }
            overlay.setText(text);
            overlay.setVisibility(View.VISIBLE);
            mHandler.removeCallbacks(overlayThread);
            // 延迟一秒后执行，让overlay为不可见
            mHandler.postDelayed(overlayThread, 1000);
        }
    }

}