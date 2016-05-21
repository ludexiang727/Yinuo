package com.yinuo.listener;

import com.baidu.location.BDLocation;

/**
 * Created by gus on 16/5/21.
 */
public interface ILocation {
    void locationSuccess(BDLocation location);
    void locationFail();
}
