package com.yinuo.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.yinuo.mode.AddressModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludexiang on 2016/5/11.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "yinuo.db";
    private static final int DB_VERSION = 1;

    /** create table begin **/
    private final String TABLE_CREATE = "create table if not exists ";
    // table
    public final String TABLE_PROVINCE_LISTS = "province_lists";
    // common param begin
    private final String TABLE_PARAM_ID = "id integer primary key autoincrement"; // common params id
    // common param end

    // params
    private final String TABLE_PARAM_PRO_ID = "pro_id";
    private final String TABLE_PARAM_PRO_NAME = "pro_name";
    private final String TABLE_PARAM_PRO_SORT = "pro_sort";
    private final String TABLE_PARAM_PRO_MARK = "pro_mark";

    // table
    public final String TABLE_CITY_LISTS = "city_lists";

    // param
    private final String TABLE_PARAM_CITY_ID = "city_id";
    private final String TABLE_PARAM_CITY_OF_PRO_ID = "city_of_pro_id";
    private final String TABLE_PARAM_CITY_NAME = "city_name";
    private final String TABLE_PARAM_CITY_PINYIN = "city_pinyin";
    private final String TABLE_PARAM_CITY_FIRST_CHAR = "city_first_char";
    private final String TABLE_PARAM_CITY_LAT = "city_lat";
    private final String TABLE_PARAM_CITY_LNG = "city_lng";
    private final String TABLE_PARAM_CITY_HOT = "city_is_hot";

    // table
    public final String TABLE_CITY_AREA_LISTS = "city_area_lists";
    // param
    private final String TABLE_PARAM_CITY_AREA_ID = "city_area_id";
    private final String TABLE_PARAM_CITY_AREA_NAME = "city_area_name";
    private final String TABLE_PARAM_CITY_AREA_PINYIN = "city_area_pinyin";
    private final String TABLE_PARAM_CITY_AREA_FIRST_CHAR = "city_area_fist_char";
    private final String TABLE_PARAM_CITY_AREA_OF_CITY_ID = "city_area_of_city_id";

    // table
    public final String TABLE_RECENT_ACCESS_CITY_LISTS = "recent_city_lists";
    // param
    public final String TABLE_PARAM_RECENT_CITY_OF_PRO_ID = "recent_of_pro_id";
    private final String TABLE_PARAM_RECENT_CITY_OF_CITY_ID = "recent_of_city_id";
    private final String TABLE_PARAM_RECENT_CITY_ACCESS_COUNT = "recent_city_count";
    /** create table end **/

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create table province_lists
        createTableProLists(sqLiteDatabase);
        createTableCityLists(sqLiteDatabase);
        createTableAreaOfCity(sqLiteDatabase);
        createTableRecentAccess(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    private void createTableProLists(SQLiteDatabase db) {
        StringBuilder builder = new StringBuilder();
        builder.append(TABLE_CREATE).append(TABLE_PROVINCE_LISTS)
        .append("(").append(TABLE_PARAM_ID).append(",")
        .append(TABLE_PARAM_PRO_ID).append(" integer not null, ")
        .append(TABLE_PARAM_PRO_NAME).append(" text(200) not null, ")
        .append(TABLE_PARAM_PRO_SORT).append(" integer not null, ")
        .append(TABLE_PARAM_PRO_MARK).append(" text(100)")
        .append(")");
        String sql = builder.toString();
        Log.e("ldx", "sql -==> " + sql);
        db.execSQL(sql);
    }

    private void createTableCityLists(SQLiteDatabase db) {
        StringBuilder builder = new StringBuilder();
        builder.append(TABLE_CREATE).append(TABLE_CITY_LISTS)
        .append("(").append(TABLE_PARAM_ID).append(",")
        .append(TABLE_PARAM_CITY_ID).append(" integer not null, ")
        .append(TABLE_PARAM_CITY_OF_PRO_ID).append(" integer not null, ")
        .append(TABLE_PARAM_CITY_NAME).append(" text(200) not null, ")
        .append(TABLE_PARAM_CITY_PINYIN).append(" text(200) not null, ")
        .append(TABLE_PARAM_CITY_FIRST_CHAR).append(" text(50), ")
        .append(TABLE_PARAM_CITY_LAT).append(" double default 0.0, ")
        .append(TABLE_PARAM_CITY_LNG).append(" double default 0.0, ")
        .append(TABLE_PARAM_CITY_HOT).append(" integer default 0, ")
        .append("foreign key ").append("(").append(TABLE_PARAM_CITY_OF_PRO_ID).append(") ")
        .append("references ").append(TABLE_PROVINCE_LISTS).append("(").append(TABLE_PARAM_PRO_ID).append(")")
        .append(")");
        String sql = builder.toString();
        Log.e("ldx", "sql -==> " + sql);
        db.execSQL(sql);
    }

    private void createTableAreaOfCity(SQLiteDatabase db) {
        StringBuilder builder = new StringBuilder();
        builder.append(TABLE_CREATE).append(TABLE_CITY_AREA_LISTS)
        .append("(").append(TABLE_PARAM_ID).append(",")
        .append(TABLE_PARAM_CITY_AREA_ID).append(" integer not null, ")
        .append(TABLE_PARAM_CITY_AREA_NAME).append(" text(200) not null, ")
        .append(TABLE_PARAM_CITY_AREA_PINYIN).append(" text(200) not null, ")
        .append(TABLE_PARAM_CITY_AREA_FIRST_CHAR).append(" text(50), ")
        .append(TABLE_PARAM_CITY_AREA_OF_CITY_ID).append(" integer not null, ")
        .append("foreign key ").append("(").append(TABLE_PARAM_CITY_AREA_OF_CITY_ID)
        .append(") ").append("references ").append(TABLE_CITY_LISTS).append("(")
        .append(TABLE_PARAM_CITY_ID).append(")")
        .append(")");
        String sql = builder.toString();
        Log.e("ldx", "sql -==> " + sql);
        db.execSQL(sql);
    }

    private void createTableRecentAccess(SQLiteDatabase db) {
        StringBuilder builder = new StringBuilder();
        builder.append(TABLE_CREATE).append(TABLE_RECENT_ACCESS_CITY_LISTS)
        .append("(").append(TABLE_PARAM_ID).append(",")
        .append(TABLE_PARAM_RECENT_CITY_OF_PRO_ID).append(" integer not null, ")
        .append(TABLE_PARAM_RECENT_CITY_OF_CITY_ID).append(" integer not null, ")
        .append(TABLE_PARAM_RECENT_CITY_ACCESS_COUNT).append(" integer default 0 not null, ")
        .append("foreign key ").append("(").append(TABLE_PARAM_RECENT_CITY_OF_PRO_ID)
        .append(") ").append("references ").append(TABLE_PROVINCE_LISTS).append("(")
        .append(TABLE_PARAM_PRO_ID).append("), ")
        .append("foreign key ").append("(").append(TABLE_PARAM_RECENT_CITY_OF_CITY_ID)
        .append(") ").append("references ").append(TABLE_CITY_LISTS).append("(")
        .append(TABLE_PARAM_CITY_ID).append(")")
        .append(")");
        String sql = builder.toString();
        Log.e("ldx", "sql -==> " + sql);
        db.execSQL(sql);
    }

    public boolean isTableEmpty(String table) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(table, new String[] {"count(*)"}, null, null,null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            if (cursor.getLong(0) > 0) {
                Log.e("ldx", "count  == " + cursor.getLong(0));
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /** about address begin */
    /** insert value to province table begin */
    public void insertValue2ProTable(AddressModel address) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(TABLE_PARAM_PRO_ID, address.getProId());
        values.put(TABLE_PARAM_PRO_NAME, address.getProvince());
        values.put(TABLE_PARAM_PRO_SORT, address.getProSort());
        values.put(TABLE_PARAM_PRO_MARK, address.getProMark());
        long rowLine = db.insert(TABLE_PROVINCE_LISTS, null, values);
        if (rowLine > 0) {
            db.setTransactionSuccessful();
            db.endTransaction();
        } else {
            db.endTransaction();
        }
    }
    /** insert value to province table end*/

    public void insertValue2CityTable(AddressModel address) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(TABLE_PARAM_CITY_ID, address.getCityId());
        values.put(TABLE_PARAM_CITY_OF_PRO_ID, address.getProId());
        values.put(TABLE_PARAM_CITY_NAME, address.getCityName());
        values.put(TABLE_PARAM_CITY_PINYIN, address.getCityPinYin());
        values.put(TABLE_PARAM_CITY_FIRST_CHAR, address.getCityFirstSpell());
        values.put(TABLE_PARAM_CITY_LAT, address.getLat());
        values.put(TABLE_PARAM_CITY_LNG, address.getLng());
        values.put(TABLE_PARAM_CITY_HOT, address.getHotCity());
        long rowLine = db.insert(TABLE_CITY_LISTS, null, values);
        if (rowLine > 0) {
            db.setTransactionSuccessful();
            db.endTransaction();
        } else {
            db.endTransaction();
        }
    }

    public void insertValue2CityAreaTable(AddressModel model) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(TABLE_PARAM_CITY_AREA_ID, model.getCityAreaId());
        values.put(TABLE_PARAM_CITY_AREA_NAME, model.getCityAreaName());
        values.put(TABLE_PARAM_CITY_AREA_PINYIN, model.getCityAreaPinYin());
        values.put(TABLE_PARAM_CITY_AREA_FIRST_CHAR, model.getCityAreaFirstSpell());
        values.put(TABLE_PARAM_CITY_AREA_OF_CITY_ID, model.getCityId());
        long rowLine = db.insert(TABLE_CITY_AREA_LISTS, null, values);
        if (rowLine > 0) {
            Log.e("ldx", "city area " + rowLine);
            db.setTransactionSuccessful();
            db.endTransaction();
        } else {
            db.endTransaction();
        }
    }

    /** obtain all city from native db */
    public List<AddressModel> getAllCity() {
        List<AddressModel> allCity = new ArrayList<AddressModel>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_CITY_LISTS, null, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                AddressModel model = new AddressModel();
                int cityId = cursor.getColumnIndexOrThrow(TABLE_PARAM_CITY_ID);
                int cityName = cursor.getColumnIndexOrThrow(TABLE_PARAM_CITY_NAME);
                int cityPinYin = cursor.getColumnIndexOrThrow(TABLE_PARAM_CITY_PINYIN);
                int cityFirstChar = cursor.getColumnIndexOrThrow(TABLE_PARAM_CITY_FIRST_CHAR);
                model.setCityId(cursor.getInt(cityId));
                model.setCityName(cursor.getString(cityName));
                model.setCityPinYin(cursor.getString(cityPinYin));
                model.setCityFirstSpell(cursor.getString(cityFirstChar));
                allCity.add(model);
            }
        }
        return allCity;
    }

    /** obtain hot city **/
    public List<AddressModel> getHotCity() {
        List<AddressModel> hotCity = new ArrayList<AddressModel>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_CITY_LISTS, null, TABLE_PARAM_CITY_HOT + " = 1", null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                AddressModel model = new AddressModel();
                int cityId = cursor.getColumnIndexOrThrow(TABLE_PARAM_CITY_ID);
                int cityName = cursor.getColumnIndexOrThrow(TABLE_PARAM_CITY_NAME);
                int cityPinYin = cursor.getColumnIndexOrThrow(TABLE_PARAM_CITY_PINYIN);
                int cityFirstChar = cursor.getColumnIndexOrThrow(TABLE_PARAM_CITY_FIRST_CHAR);
                model.setCityId(cursor.getInt(cityId));
                model.setCityName(cursor.getString(cityName));
                model.setCityPinYin(cursor.getString(cityPinYin));
                model.setCityFirstSpell(cursor.getString(cityFirstChar));
                hotCity.add(model);
            }
        }
        return hotCity;
    }

    /** obtain recent access city **/
    public List<AddressModel> getRecentCity() {
        List<AddressModel> recentCity = new ArrayList<AddressModel>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from " + TABLE_RECENT_ACCESS_CITY_LISTS + " limit 0, 3";
//        Cursor cursor = db.query(TABLE_RECENT_ACCESS_CITY_LISTS, null, "limit 0, 3", null, null, null, null);
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                AddressModel model = new AddressModel();
                int cityId = cursor.getColumnIndexOrThrow(TABLE_PARAM_CITY_ID);
                int cityName = cursor.getColumnIndexOrThrow(TABLE_PARAM_CITY_NAME);
                int cityPinYin = cursor.getColumnIndexOrThrow(TABLE_PARAM_CITY_PINYIN);
                int cityFirstChar = cursor.getColumnIndexOrThrow(TABLE_PARAM_CITY_FIRST_CHAR);
                model.setCityId(cursor.getInt(cityId));
                model.setCityName(cursor.getString(cityName));
                model.setCityPinYin(cursor.getString(cityPinYin));
                model.setCityFirstSpell(cursor.getString(cityFirstChar));
                recentCity.add(model);
            }
        }
        return recentCity;
    }

    /** obtain city base keyword */
    public List<AddressModel> getSearchCityBy(String keyword) {
        List<AddressModel> searchResult = new ArrayList<AddressModel>();
        SQLiteDatabase db = getReadableDatabase();
        StringBuilder builder = new StringBuilder();
        builder.append(TABLE_PARAM_CITY_NAME).append(" like '%").append(keyword)
                .append("%' or ").append(TABLE_PARAM_CITY_PINYIN).append(" like '%")
                .append(keyword).append("%' or ").append(TABLE_PARAM_CITY_FIRST_CHAR)
                .append(" like '%").append(keyword).append("%'");
        Cursor cursor = db.query(TABLE_CITY_LISTS, null, builder.toString(), null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                AddressModel model = new AddressModel();
                int cityId = cursor.getColumnIndexOrThrow(TABLE_PARAM_CITY_ID);
                int cityName = cursor.getColumnIndexOrThrow(TABLE_PARAM_CITY_NAME);
                int cityPinYin = cursor.getColumnIndexOrThrow(TABLE_PARAM_CITY_PINYIN);
                int cityFirstChar = cursor.getColumnIndexOrThrow(TABLE_PARAM_CITY_FIRST_CHAR);
                model.setCityId(cursor.getInt(cityId));
                model.setCityName(cursor.getString(cityName));
                model.setCityPinYin(cursor.getString(cityPinYin));
                model.setCityFirstSpell(cursor.getString(cityFirstChar));
                searchResult.add(model);
            }
        }
        return searchResult;
    }
    /** about address begin */
}
