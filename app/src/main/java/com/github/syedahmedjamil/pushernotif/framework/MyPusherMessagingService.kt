package com.github.syedahmedjamil.pushernotif.framework

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.github.syedahmedjamil.pushernotif.AppContainer
import com.github.syedahmedjamil.pushernotif.BaseApplication
import com.github.syedahmedjamil.pushernotif.ImageLoader
import com.github.syedahmedjamil.pushernotif.R
import com.github.syedahmedjamil.pushernotif.domain.NotificationEntity
import com.github.syedahmedjamil.pushernotif.usecases.AddNotificationUseCase
import com.google.firebase.messaging.RemoteMessage
import com.pusher.pushnotifications.fcm.MessagingService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Arrays
import java.util.stream.Collectors


class MyPusherMessagingService : MessagingService() {

    private lateinit var appContainer: AppContainer
    private lateinit var addNotificationUseCase: AddNotificationUseCase
    private lateinit var imageLoader: ImageLoader

    private lateinit var title: String
    private lateinit var body: String
    private lateinit var subText: String
    private lateinit var date: String
    private lateinit var link: String
    private lateinit var image: String
    private lateinit var interest: String

    override fun onCreate() {
        appContainer = (applicationContext as BaseApplication).appContainer
        addNotificationUseCase = appContainer.addNotificationUseCase
        imageLoader = appContainer.imageLoader
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        try {
            title = remoteMessage.data["title"]!!
            body = remoteMessage.data["body"]!!
            subText = remoteMessage.data["subtext"]!!
            date = remoteMessage.data["date"]!!
            link = remoteMessage.data["link"]!!
            image = remoteMessage.data["image"]!!
            interest = remoteMessage.data["interest"]!!
        }
        catch (e: Exception) {
            return
        }


        val base64Image = imageLoader.getBase64(image)

        val notification = NotificationEntity(
            title = title,
            body = body,
            subText = subText,
            image = base64Image,
            link = link,
            date = date,
            interest = interest
        )

        storeNotification(notification)
        showNotification(notification)
    }

    private fun showNotification(notification: NotificationEntity) {

        val id = System.currentTimeMillis().toInt()

        val webpage = Uri.parse(notification.link)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val chooser = Intent.createChooser(intent, "Select Browser")

        val pendingIntent = PendingIntent.getActivity(
            this,
            id,
            chooser,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        var builder = NotificationCompat.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setLargeIcon(imageLoader.getBitmap())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentTitle(notification.title)
            .setSubText(notification.subText)
            .setStyle(NotificationCompat.BigTextStyle().bigText(notification.body))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setColor(Color.BLACK)
            .setGroup("pushier")
            .setLights(Color.MAGENTA, 1000, 300)
            .setDefaults(Notification.DEFAULT_VIBRATE)

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "cn"
            val descriptionText = "cd"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system.
            notificationManager.createNotificationChannel(channel)
        }

        val activeNotifications = notificationManager.activeNotifications
        if (activeNotifications.size == 20) {
            val s = Arrays.stream(activeNotifications).sorted { statusBarNotification, t1 ->
                statusBarNotification.postTime.toString().compareTo(t1.postTime.toString())
            }.collect(Collectors.toList())
            notificationManager.cancel(s[0].id)
        }

        notificationManager.notify(id, builder.build())

    }

    private fun storeNotification(notification: NotificationEntity) {
        CoroutineScope(Dispatchers.Main).launch {
            addNotificationUseCase(notification)
        }
    }
}