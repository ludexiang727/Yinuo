package com.yinuo.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.yinuo.mode.AddressModel;

/**
 * Created by ludexiang on 2016/5/11.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "yinuo.db";
    private static final int DB_VERSION = 1;

    /** create table begin **/
    private final String TABLE_CREATE = "create table if not exists ";
    // table
    private final String TABLE_PROVINCE_LISTS = "province_lists";
    // common param begin
    private final String TABLE_PARAM_ID = "id integer primary key autoincrement"; // common params id

    // common param end
    // params
    private final String TABLE_PARAM_PRO_ID = "pro_id";
    private final String TABLE_PARAM_PRO_NAME = "pro_name";
    private final String TABLE_PARAM_PRO_SORT = "pro_sort";
    private final String TABLE_PARAM_PRO_MARK = "pro_mark";
    // table
    private final String TABLE_CITY_LISTS = "city_lists";
    // param
    private final String TABLE_PARAM_CITY_ID = "city_id";
    private final String TABLE_PARAM_CITY_OF_PRO_ID = "city_of_pro_id";
    private final String TABLE_PARAM_CITY_NAME = "city_name";
    private final String TABLE_PARAM_CITY_PINYIN = "city_pinyin";
    private final String TABLE_PARAM_CITY_FIRST_CHAR = "city_first_char";
    private final String TABLE_PARAM_CITY_LAT = "city_lat";
    private final String TABLE_PARAM_CITY_LNG = "city_lng";
    private final String TABLE_PARAM_CITY_HOT = "city_is_hot";
    /** create table end **/

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create table province_lists
        createTableProLists(sqLiteDatabase);
        createTableCityLists(sqLiteDatabase);
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
        values.put(TABLE_PARAM_CITY_NAME, address.getCity());
        values.put(TABLE_PARAM_CITY_PINYIN, address.getCityPinYin());
        values.put(TABLE_PARAM_CITY_FIRST_CHAR, address.getCityFirstSpell());
        values.put(TABLE_PARAM_CITY_LAT, address.getLat());
        values.put(TABLE_PARAM_CITY_LNG, address.getLng());
        values.put(TABLE_PARAM_CITY_HOT, address.getHotCity());
        long rowLine = db.insert(TABLE_CITY_LISTS, null, values);
        if (rowLine > 0) {
            Log.e("ldx", "success.............");
            db.setTransactionSuccessful();
            db.endTransaction();
        } else {
            db.endTransaction();
        }
    }
}
