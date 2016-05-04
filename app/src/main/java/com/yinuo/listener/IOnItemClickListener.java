package com.yinuo.listener;

import android.view.View;

import com.yinuo.base.BaseObject;

/**
 * Created by gus on 16/4/17.
 */
public interface IOnItemClickListener <T extends BaseObject> {
    void onItemClick(View v, T t, int position);
}
