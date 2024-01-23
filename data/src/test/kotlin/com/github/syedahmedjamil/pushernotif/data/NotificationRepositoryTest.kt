package com.github.syedahmedjamil.pushernotif.data

import com.github.syedahmedjamil.pushernotif.domain.NotificationDataSource
import com.github.syedahmedjamil.pushernotif.domain.NotificationEntity
import com.github.syedahmedjamil.pushernotif.domain.NotificationRepository
import com.github.syedahmedjamil.pushernotif.shared_test.fakes.source.FakeNotificationLocalDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NotificationRepositoryTest {

    private lateinit var repository: NotificationRepository
    private lateinit var dataSource: NotificationDataSource

    @Before
    fun setUp() {
        dataSource = FakeNotificationLocalDataSource(
            listOf(
                NotificationEntity(
                    "title1", "body1", "subtext1",
                    "image1", "link1", "date1", "test1"
                ),
                NotificationEntity(
                    "title2", "body2", "subtext2",
                    "image2", "link2", "date2", "test1"
                ),
                NotificationEntity(
                    "title1", "body1", "subtext1",
                    "image1", "link1", "date1", "test2"
                ),
                NotificationEntity(
                    "title2", "body2", "subtext2",
                    "image2", "link2", "date2", "test2"
                )
            )
        )
        repository = NotificationRepositoryImpl(dataSource)
    }

    @Test
    fun `should get notification`() = runTest {
        // given
        val interest1 = "test1"
        val interest2 = "test2"
        val expected1 = listOf(
            NotificationEntity(
                "title1", "body1", "subtext1",
                "image1", "link1", "date1", "test1"
            ),
            NotificationEntity(
                "title2", "body2", "subtext2",
                "image2", "link2", "date2", "test1"
            )
        )
        val expected2 = listOf(
            NotificationEntity(
                "title1", "body1", "subtext1",
                "image1", "link1", "date1", "test2"
            ),
            NotificationEntity(
                "title2", "body2", "subtext2",
                "image2", "link2", "date2", "test2"
            )
        )
        // when
        val actual1 = repository.getNotifications(interest1).first()
        val actual2 = repository.getNotifications(interest2).first()
        // then
        assertEquals(expected1, actual1)
        assertEquals(expected2, actual2)
    }

    @Test
    fun `should add notification`() = runTest {
        // given
        val notification = NotificationEntity(
            "title2", "body2", "subtext2",
            "image2", "link2", "date2", "test2"
        )
        // when
        repository.addNotification(notification)
        // then
        Assert.assertTrue((dataSource as FakeNotificationLocalDataSource).isAddNotificationCalled)
    }

    @Test
    fun `should delete notifications`() = runTest {
        // given
        // when
        repository.deleteNotifications()
        // then
        Assert.assertTrue((dataSource as FakeNotificationLocalDataSource).isDeleteNotificationsCalled)
    }
}