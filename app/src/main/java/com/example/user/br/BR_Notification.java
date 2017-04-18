package com.example.user.br;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.TextView;

/**
 * Created by user on 2017/4/18.
 */

public class BR_Notification extends BroadcastReceiver {
    static int id = 70000;

    TextView tv;
    @Override
    public void onReceive(Context context, Intent intent) {
        //this.tv=(TextView)findViewByID();
        String msg = intent.getStringExtra("KEY_MSG");

        Intent newintent = new Intent();
        newintent.setClass(context, MainActivity.class);
        newintent.putExtra("name",msg);

        PendingIntent pendingIntent = PendingIntent.
                getActivity(context, 0, newintent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notify = null;
        if (Build.VERSION.SDK_INT >= 16) {
            notify = newNotification(context, pendingIntent,
                    "(New) Broadcast is received.", msg);
        } else {
            notify = oldNotification(context, pendingIntent,
                    "(Old) Broadcast is received.", msg);
        }

        NotificationManager notificationManager =
                (NotificationManager)context.
                        getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(id++, notify);
    }

    @SuppressWarnings("deprecation")
    private Notification oldNotification(
            Context context, PendingIntent pi,
            String title, String msg) {

        Notification notify = new Notification(
                R.mipmap.ic_launcher, title,
                System.currentTimeMillis());
        //notify.setLatestEventInfo(context, title, msg, pi);
        return notify;
    }

    @SuppressLint("NewApi")
    private Notification newNotification(
            Context context, PendingIntent pi,
            String title, String msg) {

        Notification.Builder builder =
                new Notification.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(msg);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pi);
        builder.setTicker(msg);
        builder.setWhen(System.currentTimeMillis());
        Notification notify = builder.build();
        return notify;
    }

}
