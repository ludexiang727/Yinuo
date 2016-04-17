package com.yinuo.listener;

import com.yinuo.base.BaseObject;

/**
 * Created by gus on 16/4/17.
 */
public interface IOnItemClickListener <T extends BaseObject> {
    void onItemClick(T t, int position);
}
