package com.github.syedahmedjamil.pushernotif.test.integration

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.github.syedahmedjamil.pushernotif.AppContainer
import com.github.syedahmedjamil.pushernotif.ImageLoader
import com.github.syedahmedjamil.pushernotif.usecases.AddNotificationUseCase
import com.github.syedahmedjamil.pushernotif.usecases.DeleteNotificationsUseCase
import com.github.syedahmedjamil.pushernotif.usecases.GetNotificationsUseCase
import org.junit.Assert
import org.junit.Test

class AppContainerTest {

    private val context: Context = ApplicationProvider.getApplicationContext()
    private val appContainer: AppContainer = AppContainer(context)

    @Test
    fun test_appContainer_is_not_null() {
        Assert.assertNotNull(appContainer)
    }

    @Test
    fun test_appContainer_has_a_addNotificationUseCase() {
        val dep: AddNotificationUseCase = appContainer.addNotificationUseCase
        Assert.assertNotNull(dep)
    }

    @Test
    fun test_appContainer_has_a_getNotificationsUseCase() {
        val dep: GetNotificationsUseCase = appContainer.getNotificationsUseCase
        Assert.assertNotNull(dep)
    }

    @Test
    fun test_appContainer_has_a_deleteNotificationsUseCase() {
        val dep: DeleteNotificationsUseCase = appContainer.deleteNotificationsUseCase
        Assert.assertNotNull(dep)
    }

    @Test
    fun test_appContainer_has_a_imageLoader() {
        val dep: ImageLoader = appContainer.imageLoader
        Assert.assertNotNull(dep)
    }
}