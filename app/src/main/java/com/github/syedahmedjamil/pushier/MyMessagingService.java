package com.github.syedahmedjamil.pushier;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.util.Base64;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.github.syedahmedjamil.pushier.R;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.pusher.pushnotifications.fcm.MessagingService;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyMessagingService extends MessagingService {
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Map<String, String> data = remoteMessage.getData();
        sendNotification(data);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sendNotification(Map<String, String> data) {
        String title = data.get("title");
        String body = data.get("body");
        String subtext = data.get("subtext");
        String link = data.get("link");
        String image = data.get("image");
        String interest = data.get("interest");
        String date = data.get("date");
        String category = data.get("category");

        SharedPreferences notificationsSP = getSharedPreferences(interest, MODE_PRIVATE);
        SharedPreferences dataSP = getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences localSP = getSharedPreferences("local", MODE_PRIVATE);
        String oldImage = localSP.getString(interest + "imageUrl", "");

        int startIndex = dataSP.getInt(interest + "-startIndex", -2);
        int endIndex = dataSP.getInt(interest + "-endIndex", -2);
        endIndex = (endIndex + 1) % Global.MAX_NOTIFICATIONS;
        if (startIndex == endIndex) {
            startIndex = (startIndex + 1) % Global.MAX_NOTIFICATIONS;
        }
        if (startIndex == -1) {
            startIndex = 0;
        }
        NotificationItem notificationItem = new NotificationItem(title, link, date, image,interest);
        Gson gson = new Gson();
        String notificationItemJson = gson.toJson(notificationItem);
        notificationsSP.edit().putString(String.valueOf(endIndex), notificationItemJson).apply();
        dataSP.edit().putInt(interest + "-startIndex", startIndex).apply();
        dataSP.edit().putInt(interest + "-endIndex", endIndex).apply();

        Bitmap icon = null;
        try {
            icon = Picasso.get().load(image).get();
            if (!oldImage.equals(image)) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                icon.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                localSP.edit().putString(interest + "-imageUrl", image).apply();
                localSP.edit().putString(interest + "-imageEncoded", encodedImage).apply();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//      byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
//      Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        int id = (int) System.currentTimeMillis();

        Uri webpage = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Intent chooser = Intent.createChooser(intent, "Select Browser");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, id, chooser, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(title)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setSubText(subtext)
                .setLargeIcon(icon)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setColor(Color.BLACK)
                .setGroup("pushier")
                .setChannelId(category+"id")
                .setLights(Color.MAGENTA, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.ic_android_black_24dp);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Notification Channel is required for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String channelName = "";
            String channelId = "";
            String channelDescription = "";

            NotificationChannel channel = new NotificationChannel(
                    category+"id", category, NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.MAGENTA);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
            notificationManager.createNotificationChannel(channel);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarNotification[] activeNotifications = notificationManager.getActiveNotifications();
            if (activeNotifications.length == 20) {
                List<StatusBarNotification> s = Arrays.stream(activeNotifications).sorted(new Comparator<StatusBarNotification>() {
                    @Override
                    public int compare(StatusBarNotification statusBarNotification, StatusBarNotification t1) {
                        return String.valueOf(statusBarNotification.getPostTime()).compareTo(String.valueOf(t1.getPostTime()));
                    }
                }).collect(Collectors.toList());
                notificationManager.cancel(s.get(0).getId());
            }
        }

        notificationManager.notify(id, notificationBuilder.build());

    }
}