/*
 * Copyright © 2016, Malyshev Vladislav,  thestrangesquirrel@gmail.com. This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package ua.pp.squirrel.android.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import net.squirrel.poshtar.client.activity.SaveTrackActivity;
import net.squirrel.poshtar.client.activity.SaveTracksActivity;
import net.squirrel.poshtar.dto.SavedTrack;
import net.squirrel.postar.client.R;

import java.util.ArrayList;
import java.util.List;


public class AppNotificationManager {
    public static final int MIN_SDK_STACK_NOTIFICATION = Build.VERSION_CODES.M;
    private static final String SINGLE_NOTIFY_TAG = "ua.pp.squirrel.poshtar.single-not";
    private static final String STACK_NOTIFY_TAG = "ua.pp.squirrel.poshtar.stack-not";
    private static final int MAX_COUNT_SINGLE_NOTIFICATION = 2;
    private final int appStackNotificationId;
    private final String appPackage;

    private Context context;
    private NotificationManager notificationManager;
    private List<StatusBarNotification> singleAppNotifications;
    private StatusBarNotification stackAppNotification;
    private SavedTrack trackForNotification;

    public AppNotificationManager(Context context) {
        this.context = context;
        appPackage = context.getPackageName();
        appStackNotificationId = appPackage.hashCode();
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void sendNotification(SavedTrack track) {
        trackForNotification = track;
        sendCheckDeviceVersion();
    }


    private void sendCheckDeviceVersion() {
        if (Build.VERSION.SDK_INT >= MIN_SDK_STACK_NOTIFICATION) {
            findAppNotification();
            sendStackNotificationIfNeed();
        } else {
            singleNotification();
        }
    }


    @RequiresApi(api = MIN_SDK_STACK_NOTIFICATION)
    private void findAppNotification() {
        StatusBarNotification[] activeNotifications = notificationManager.getActiveNotifications();
        singleAppNotifications = new ArrayList<>();
        for (StatusBarNotification notification : activeNotifications) {
            final String notificationPackageName = notification.getPackageName();
            if (appPackage.equals(notificationPackageName)) {
                final String tag = notification.getTag();
                if (STACK_NOTIFY_TAG.equals(tag)) {
                    stackAppNotification = notification;
                    return;
                }
                singleAppNotifications.add(notification);
            }
        }
    }

    @RequiresApi(api = MIN_SDK_STACK_NOTIFICATION)
    private void sendStackNotificationIfNeed() {
        if ((stackAppNotification != null) || (singleAppNotifications.size() >= MAX_COUNT_SINGLE_NOTIFICATION)) {
            stackNotification();
        } else {
            singleNotification();
        }
    }

    @RequiresApi(api = MIN_SDK_STACK_NOTIFICATION)
    private void stackNotification() {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(context.getResources().getText(R.string.notification_stack_header));
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.notif)
                .setContentTitle(context.getResources().getText(R.string.notification_stack_header))
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);

        pickingOldNotificationInfo(inboxStyle);
        inboxStyle.addLine(trackForNotification.trackNumber);
        notificationBuilder.setStyle(inboxStyle);
        notificationBuilder.setContentIntent(buildPendingIntentWithStackFoStackN());
        final Notification notification = notificationBuilder.build();
        notificationManager.notify(STACK_NOTIFY_TAG, appStackNotificationId, notification);
    }

    @RequiresApi(api = MIN_SDK_STACK_NOTIFICATION)
    private void pickingOldNotificationInfo(NotificationCompat.InboxStyle inboxStyle) {
        if (stackAppNotification == null) {
            pickingOfSingleNotification(inboxStyle);
        } else {
            pickingOfOldStackNotification(inboxStyle);
        }
    }

    @RequiresApi(api = MIN_SDK_STACK_NOTIFICATION)
    private void pickingOfSingleNotification(NotificationCompat.InboxStyle inboxStyle) {
        for (StatusBarNotification notification : singleAppNotifications) {
            String line = notification.getNotification().extras.getString(NotificationCompat.EXTRA_TEXT);
            inboxStyle.addLine(line);
            clearOldAppNotification(notification);
        }
    }

    @RequiresApi(api = MIN_SDK_STACK_NOTIFICATION)
    private void pickingOfOldStackNotification(NotificationCompat.InboxStyle inboxStyle) {
        Bundle extras = stackAppNotification.getNotification().extras;
        CharSequence lines[] = extras.getCharSequenceArray(NotificationCompat.EXTRA_TEXT_LINES);
        for (CharSequence line : lines) {
            if (line != null) {
                inboxStyle.addLine(line);
            }
        }
        notificationManager.cancel(STACK_NOTIFY_TAG, stackAppNotification.getId());
        stackAppNotification = null;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void clearOldAppNotification(StatusBarNotification notification) {
        notificationManager.cancel(SINGLE_NOTIFY_TAG, notification.getId());
    }

    private PendingIntent buildPendingIntentWithStackFoStackN() {
        Intent targetIntent = new Intent(context, SaveTracksActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(SaveTracksActivity.class);
        stackBuilder.addNextIntent(targetIntent);

        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void singleNotification() {
        String notificationText = trackForNotification.trackNumber;
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.notif)
                .setAutoCancel(true)
                .setContentTitle(context.getResources().getText(R.string.notification_single_title))
                .setContentText(notificationText)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
        notificationBuilder.setContentIntent(buildPendingIntentWithStackFoOneN(trackForNotification));

        notificationManager.notify(SINGLE_NOTIFY_TAG, trackForNotification.id, notificationBuilder.build());
    }

    private PendingIntent buildPendingIntentWithStackFoOneN(SavedTrack track) {
        Intent targetIntent = new Intent(context, SaveTrackActivity.class);
        targetIntent.putExtra(SaveTracksActivity.PARAM_SAVE_TRACK, track);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(SaveTrackActivity.class);
        stackBuilder.addNextIntent(targetIntent);

        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
