package com.github.syedahmedjamil.pushernotif.test.integration

import android.app.Service
import android.content.Context
import android.content.ContextWrapper
import androidx.test.core.app.ApplicationProvider
import com.github.syedahmedjamil.pushernotif.AppContainer
import com.github.syedahmedjamil.pushernotif.BaseApplication
import com.github.syedahmedjamil.pushernotif.ImageLoader
import com.github.syedahmedjamil.pushernotif.core.Result
import com.github.syedahmedjamil.pushernotif.domain.NotificationEntity
import com.github.syedahmedjamil.pushernotif.framework.MyPusherMessagingService
import com.github.syedahmedjamil.pushernotif.shared_test.fakes.usecase.FakeAddNotificationUseCase
import com.github.syedahmedjamil.pushernotif.usecases.AddNotificationUseCase
import com.google.firebase.messaging.RemoteMessage
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MyPusherMessagingServiceTest {

    private lateinit var context: Context
    private lateinit var appContainer: AppContainer
    private lateinit var messagingService: MyPusherMessagingService
    private lateinit var addNotificationUseCase: AddNotificationUseCase
    private lateinit var imageLoader: ImageLoader
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher + Job())


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        context = ApplicationProvider.getApplicationContext()
        messagingService = MyPusherMessagingService()
        addNotificationUseCase = FakeAddNotificationUseCase()
        imageLoader = FakeImageLoader()
        appContainer = (context as BaseApplication).appContainer
        appContainer.addNotificationUseCase = addNotificationUseCase
        appContainer.imageLoader = imageLoader
    }

    @Test
    fun should_store_notification() = testScope.runTest {
        // given
        val usecase = addNotificationUseCase as FakeAddNotificationUseCase
        val map = mutableMapOf<String, String>(
            "title" to "title1",
            "body" to "body1",
            "subtext" to "subtext1",
            "date" to "date1",
            "link" to "link1",
            "image" to "image1",
            "interest" to "interest1"
        )
        val message: RemoteMessage = mockk<RemoteMessage>()
        every { message.data } returns map
        val notification = NotificationEntity(
            title ="title1",
            body = "body1",
            subText = "subtext1",
            date ="date1",
            link = "link1",
            image = "base64Image",
            interest = "interest1"
        )
        // when
        messagingService.attachBaseContext()
        messagingService.onCreate()
        messagingService.onMessageReceived(message)
        advanceUntilIdle()
        // then
        assertEquals(
            notification,
            FakeAddNotificationUseCase.storedNotification
        )
        assertTrue(usecase.result is Result.Success)
    }

    internal fun Service.attachBaseContext() {
        val context: Context = ApplicationProvider.getApplicationContext()

        val attachBaseContextMethod =
            ContextWrapper::class.java.getDeclaredMethod("attachBaseContext", Context::class.java)
        attachBaseContextMethod.isAccessible = true

        attachBaseContextMethod.invoke(this, context)
    }
}


