package com.github.syedahmedjamil.pushernotif.framework

import androidx.annotation.VisibleForTesting
import com.github.syedahmedjamil.pushernotif.AppContainer
import com.github.syedahmedjamil.pushernotif.BaseApplication
import com.github.syedahmedjamil.pushernotif.domain.ImageLoader
import com.github.syedahmedjamil.pushernotif.domain.NotificationEntity
import com.github.syedahmedjamil.pushernotif.usecases.AddNotificationUseCase
import com.google.firebase.messaging.RemoteMessage
import com.pusher.pushnotifications.fcm.MessagingService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyPusherMessagingService : MessagingService() {

    private lateinit var appContainer: AppContainer
    private lateinit var addNotificationUseCase: AddNotificationUseCase
    private lateinit var imageLoader: ImageLoader

    override fun onCreate() {
        appContainer = (applicationContext as BaseApplication).appContainer
        addNotificationUseCase = appContainer.addNotificationUseCase
        imageLoader = appContainer.imageLoader
    }

    @VisibleForTesting
    companion object {
        var isOnMessageReceivedCalled = false
        lateinit var data: MutableMap<String, String>
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        data = remoteMessage.data
        isOnMessageReceivedCalled = true

        val title = remoteMessage.data["title"]!!
        val body = remoteMessage.data["body"]!!
        val subText = remoteMessage.data["subtext"]!!
        val date = remoteMessage.data["date"]!!
        val link = remoteMessage.data["link"]!!
        val image = remoteMessage.data["image"]!!
        val interest = remoteMessage.data["interest"]!!

        val base64Image = imageLoader.load(image)

        val notification = NotificationEntity(
            title = title,
            body = body,
            subText = subText,
            image = base64Image,
            link = link,
            date = date,
            interest = interest
        )

        CoroutineScope(Dispatchers.Main).launch {
            addNotificationUseCase(notification)
        }
    }


}