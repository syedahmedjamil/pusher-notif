package com.github.syedahmedjamil.pushernotif.framework

import androidx.annotation.VisibleForTesting
import com.google.firebase.messaging.RemoteMessage
import com.pusher.pushnotifications.fcm.MessagingService

class MyPusherMessagingService : MessagingService() {

    @VisibleForTesting
    companion object {
        var isOnMessageReceivedCalled = false
        lateinit var data: MutableMap<String, String>
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        data = remoteMessage.data
        isOnMessageReceivedCalled = true
    }
}