package com.omi.app.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.omi.app.R;
import com.omi.app.activity.HomeActivity;
import com.omi.app.db.DBHelper;
import com.omi.app.utils.Constants;
import com.omi.app.utils.SharePreferenceUtility;

import static com.omi.app.utils.Constants.AppConst.CART_COUNT;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String ADMIN_CHANNEL_ID = "admin_channel";
    private static final String IMAGE_URL_EXTRA = "imageUrl";
    private static final int NOTIFICATION_ID = 6578;
    private static final String NOTIFICATION_ID_EXTRA = "notificationId";
    private static final int NOTI_PRIMARY1 = 1100;
    private static final int NOTI_PRIMARY2 = 1101;
    private static final int NOTI_SECONDARY1 = 1200;
    private static final int NOTI_SECONDARY2 = 1201;
    /* renamed from: c */
//    Calendar f25c;
    String formattedDate;
    private NotificationHelper notificationHelper;
    private NotificationManager notificationManager;
//    ArrayList<PushBotsModel> pushBotsModels;

    public void onNewToken(String s) {
        super.onNewToken(s);
        SharePreferenceUtility.saveStringPreferences(this, Constants.AppConst.FCM_TOKEN,s);
    }

    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            showNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        } else if (remoteMessage != null) {
            showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));
        }
    }

    private void showNotification(String title, String body) {

        String count = (String) SharePreferenceUtility.getPreferences(this, CART_COUNT, SharePreferenceUtility.PREFTYPE_STRING);
        int tmp_count;

        if (count.isEmpty()) {
            tmp_count = 1;
        } else {
            tmp_count = Integer.parseInt(count) + 1;
        }

        SharePreferenceUtility.saveStringPreferences(this, CART_COUNT, String.valueOf(tmp_count));
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.insertNote(title, body);
        this.notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            this.notificationHelper = new NotificationHelper(this, this.notificationManager);
            sendNotification(NOTI_PRIMARY1, title, body);
            return;
        }

        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        final PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        Notification.Builder notificationBuilder = new Notification
                .Builder(this)
                .setContentText(body)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setLights(Color.RED, 3000, 3000);



        if (Build.VERSION.SDK_INT >= 21) {
            notificationBuilder.setSmallIcon(R.drawable.omi_logo);
            notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.omi_logo));
            notificationBuilder.setColor(getResources().getColor(R.color.white));
        } else {
            notificationBuilder.setSmallIcon(R.drawable.omi_logo);
            notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.omi_logo));
        }
        Notification notification1 = notificationBuilder.build();
        this.notificationManager.notify(NOTIFICATION_ID, notification1);
    }

    @RequiresApi(api = 26)
    public void sendNotification(int id, String title, String msg) {
        Notification.Builder nb = null;
        switch (id) {
            case NOTI_PRIMARY1 /*1100*/:
                nb = this.notificationHelper.getNotification1(title, msg);
                break;
            case NOTI_PRIMARY2 /*1101*/:
                nb = this.notificationHelper.getNotification1(title, msg);
                break;
            case NOTI_SECONDARY1 /*1200*/:
                nb = this.notificationHelper.getNotification2(title, msg);
                break;
            case NOTI_SECONDARY2 /*1201*/:
                nb = this.notificationHelper.getNotification2(title, msg);
                break;
            default:
                break;
        }
        if (nb != null) {
            this.notificationHelper.notify(id, nb);
        }
    }

}
