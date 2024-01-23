package com.github.syedahmedjamil.pushernotif

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.github.syedahmedjamil.pushernotif.data.NotificationRepositoryImpl
import com.github.syedahmedjamil.pushernotif.framework.NotificationLocalDataSource
import com.github.syedahmedjamil.pushernotif.framework.PicassoImageLoader
import com.github.syedahmedjamil.pushernotif.usecases.AddNotificationUseCase
import com.github.syedahmedjamil.pushernotif.usecases.AddNotificationUseCaseImpl
import com.github.syedahmedjamil.pushernotif.usecases.DeleteNotificationsUseCase
import com.github.syedahmedjamil.pushernotif.usecases.DeleteNotificationsUseCaseImpl
import com.github.syedahmedjamil.pushernotif.usecases.GetNotificationsUseCase
import com.github.syedahmedjamil.pushernotif.usecases.GetNotificationsUseCaseImpl
import kotlinx.coroutines.runBlocking

private const val NOTIFICATION_DATASTORE_NAME = "notification"

class AppContainer(context: Context) {

    // Some dependencies are provided using Hilt

    private val notificationDataStore by lazy {
        PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(NOTIFICATION_DATASTORE_NAME) }
        )
    }

    private val notificationLocalDataSource by lazy {
        NotificationLocalDataSource(
            notificationDataStore
        )
    }

    private val notificationRepository by lazy {
        NotificationRepositoryImpl(
            notificationLocalDataSource
        )
    }

    val getNotificationsUseCase: GetNotificationsUseCase =
        GetNotificationsUseCaseImpl(notificationRepository)

    val deleteNotificationsUseCase: DeleteNotificationsUseCase =
        DeleteNotificationsUseCaseImpl(notificationRepository)

    // These are var because they are used in tests to replace with fakes
    var addNotificationUseCase: AddNotificationUseCase =
        AddNotificationUseCaseImpl(notificationRepository)
    var imageLoader: ImageLoader = PicassoImageLoader()


    // For testing
    @VisibleForTesting
    fun reset() {
        runBlocking {
//            interestDataStore.edit { it.clear() }
        }
    }
}