package com.yinuo.utils;

import com.yinuo.adapter.EventBusAdapter;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by ludexiang on 2016/5/24.
 */
public class MessageEventUtil {
    private EventBusAdapter mEventAdapter;

    private static MessageEventUtil sMessageUtil;

    private MessageEventUtil() {}

    public synchronized static MessageEventUtil getInstance() {
        return MessageEventFactory.create();
    }

    private static final class MessageEventFactory {
         static MessageEventUtil create() {
            if (sMessageUtil == null) {
                sMessageUtil = new MessageEventUtil();
            }
            return sMessageUtil;
        }
    }

    public void setEventAdapter(EventBusAdapter adapter) {
        mEventAdapter = adapter;
    }

    public void register() {
        if (mEventAdapter == null) {
            throw new IllegalArgumentException("register error argument is error");
        }
        EventBus.getDefault().register(mEventAdapter);
    }

    public void post() {
        if (mEventAdapter == null) {
            throw new IllegalArgumentException("post error argument is null");
        }
        EventBus.getDefault().post(mEventAdapter);
    }


    public void unRegister() {
        if (mEventAdapter == null) {
            throw new IllegalArgumentException("unRegister error argument is null");
        }
        EventBus.getDefault().unregister(mEventAdapter);
        mEventAdapter = null;
    }
}
