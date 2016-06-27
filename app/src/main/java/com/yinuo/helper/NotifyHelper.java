package com.yinuo.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.yinuo.base.BaseApplication;
import com.yinuo.ui.MainActivity;

/**
 * Created by ludexiang on 16/6/27.
 * <p/>
 * PendingIntent是一种特殊的Intent。主要的区别在于Intent的执行立刻的，
 * 而PendingIntent的执行不是立刻的。PendingIntent执行的操作实质上是参数传进来的Intent的操作，
 * 但是使用PendingIntent的目的在于它所包含的Intent的操作的执行是需要满足某些条件的。
 * PendingIntent包含了Intent及Context，所以就算Intent所属程序结束
 * PendingIntent依然有效，可以在其他程序中使用。
 * Intent和PendingIntent的区别
 * a. Intent是立即使用的，而PendingIntent可以等到事件发生后触发，PendingIntent可以cancel
 * b. Intent在程序结束后即终止，而PendingIntent在程序结束后依然有效
 * c. PendingIntent自带Context，而Intent需要在某个Context内运行
 * d. Intent在原task中运行，PendingIntent在新的task中运行
 */

public class NotifyHelper {

    private static NotifyHelper sNotifyHelper;

    private NotifyHelper() {

    }

    private static final class NotifyFactory {
        private static NotifyHelper create() {
            if (sNotifyHelper == null) {
                synchronized (NotifyFactory.class) {
                    sNotifyHelper = new NotifyHelper();
                }
            }
            return sNotifyHelper;
        }
    }

    public static NotifyHelper getInstance() {
        return NotifyFactory.create();
    }

    public void notify(String msg) {
        Context context = BaseApplication.getInstance().getContext();
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int icon = android.R.drawable.stat_notify_chat;
        long when = System.currentTimeMillis();
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(icon);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setWhen(when);
        builder.setContentText(msg);
        builder.setContentIntent(pendingIntent);
        //自定义震动模式，数组中数字依次是静止时长，震动时长，静止时长，震动时长...单位毫秒
        builder.setVibrate(new long[]{0, 50, 100, 150});
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.build();
        } else {
            notification = new Notification();
        }
        notificationManager.notify(0, notification);
    }
}
