package com.yinuo.net;

import com.yinuo.net.base.NetBaseObject;

/**
 * Created by ludexiang on 2016/4/8.
 */
public interface IRequestListener {
    void onSuccess(NetBaseObject object);
    void onFail(NetBaseObject object);
}
