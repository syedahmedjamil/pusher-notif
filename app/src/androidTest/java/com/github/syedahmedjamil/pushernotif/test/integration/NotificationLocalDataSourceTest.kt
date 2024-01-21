package com.github.syedahmedjamil.pushernotif.test.integration

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.github.syedahmedjamil.pushernotif.domain.NotificationDataSource
import com.github.syedahmedjamil.pushernotif.domain.NotificationEntity
import com.github.syedahmedjamil.pushernotif.framework.NotificationLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File

private const val TEST_DATASTORE_NAME: String = "test_notification"

@MediumTest
class NotificationLocalDataSourceTest {
    private lateinit var notificationDataSource: NotificationDataSource
    private val testContext: Context = ApplicationProvider.getApplicationContext()
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher + Job())
    private val testDataStore: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(scope = testScope,
            produceFile = {
                testContext.preferencesDataStoreFile(TEST_DATASTORE_NAME)
            }
        )

    private fun createTestDataStoreFile(fileName: String): File {
        val context = InstrumentationRegistry.getInstrumentation().context
        val testDataStoreFile = context.assets.open(fileName)
        val file = File(testContext.filesDir, "datastore/test_notification.preferences_pb")
        file.parentFile?.mkdir()
        file.createNewFile()
        file.outputStream().use {
            testDataStoreFile.copyTo(it)
        }
        return file
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        notificationDataSource = NotificationLocalDataSource(testDataStore)
    }

    @After
    fun tearDown() {
        File(testContext.filesDir, "datastore").deleteRecursively()
    }

    @Test
    fun should_get_empty_list_when_notifications_not_exists() = testScope.runTest {
        // given
        val expected = emptyList<NotificationEntity>()
        // when
        val actual = notificationDataSource.getNotifications("test").first()
        // then
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun should_get_notifications_when_exists() = testScope.runTest {
        // given
        createTestDataStoreFile("test_notification.preferences_pb")
        val interest1 = "test1"
        val interest2 = "test2"

        val expected1 = listOf(
            NotificationEntity(
                "test", "test", "test",
                "test", "test", "test", "test1"
            )
        )
        val expected2 = listOf(
            NotificationEntity(
                "test", "test", "test",
                "test", "test", "test", "test2"
            )
        )
        // when
        val actual1 = notificationDataSource.getNotifications(interest1).first()
        val actual2 = notificationDataSource.getNotifications(interest2).first()

        // then
        assertEquals(expected1, actual1)
        assertEquals(expected2, actual2)
    }

}