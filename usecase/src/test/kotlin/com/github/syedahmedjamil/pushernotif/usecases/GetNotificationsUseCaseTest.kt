package com.github.syedahmedjamil.pushernotif.usecases

import com.github.syedahmedjamil.pushernotif.domain.NotificationEntity
import com.github.syedahmedjamil.pushernotif.domain.NotificationRepository
import com.github.syedahmedjamil.pushernotif.shared_test.fakes.repository.FakeNotificationRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetNotificationsUseCaseTest {

    private lateinit var useCase: GetNotificationsUseCase
    private lateinit var repository: NotificationRepository

    @Before
    fun setUp() {
        repository = FakeNotificationRepository(
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
        useCase = GetNotificationsUseCaseImpl(repository)
    }

    // Business Tests (For Users)
    // These tests contribute to the features of the app.

    @Test
    fun `should get notifications`() = runTest {
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
        val actual1 = useCase(interest1).first()
        val actual2 = useCase(interest2).first()
        // then
        assertEquals(expected1, actual1)
        assertEquals(expected2, actual2)
    }
}