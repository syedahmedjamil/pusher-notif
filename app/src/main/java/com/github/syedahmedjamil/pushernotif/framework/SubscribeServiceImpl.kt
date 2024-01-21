package com.github.syedahmedjamil.pushernotif.framework

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import com.github.syedahmedjamil.pushernotif.domain.SubscribeService
import com.pusher.pushnotifications.PushNotifications
import javax.inject.Inject

class SubscribeServiceImpl @Inject constructor(private val context: Context) : SubscribeService {
    override suspend fun subscribe(instanceId: String, interests: List<String>) {
        PushNotifications.start(context, instanceId)
        PushNotifications.setDeviceInterests(interests.toSet())
    }

    override fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(context, ConnectivityManager::class.java) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        return activeNetwork != null
    }
}