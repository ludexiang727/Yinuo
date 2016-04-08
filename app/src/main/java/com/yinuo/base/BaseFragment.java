package com.yinuo.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yinuo.net.IRequestListener;
import com.yinuo.net.base.NetBaseObject;
import com.yinuo.ui.component.widget.Loading;

/**
 * Created by ludexiang on 2016/4/5.
 */
public abstract class BaseFragment extends Fragment implements IRequestListener<NetBaseObject> {

    public abstract int pageLayoutId();

    public abstract void initialize(View view);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(pageLayoutId(), container, false);
        initialize(view);
        return view;
    }

    @Override
    public void onFail(NetBaseObject object) {
    }

    @Override
    public void onSuccess(NetBaseObject object) {
    }
}
