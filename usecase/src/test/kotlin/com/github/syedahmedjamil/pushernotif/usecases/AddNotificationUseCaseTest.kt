package com.github.syedahmedjamil.pushernotif.usecases

import com.github.syedahmedjamil.pushernotif.core.Result
import com.github.syedahmedjamil.pushernotif.domain.NotificationEntity
import com.github.syedahmedjamil.pushernotif.domain.NotificationRepository
import com.github.syedahmedjamil.pushernotif.shared_test.fakes.repository.FakeNotificationRepository
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AddNotificationUseCaseTest {
    private lateinit var useCase: AddNotificationUseCase
    private lateinit var repository: NotificationRepository
    private val testDispatcher = StandardTestDispatcher()
    private val scope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        repository = FakeNotificationRepository()
        useCase = AddNotificationUseCaseImpl(repository, testDispatcher)
    }

    // Business Tests (For Users)
    // These tests contribute to the features of the app.

    @Test
    fun `should return success when notification is added`() = scope.runTest {
        // given
        (repository as FakeNotificationRepository).isSystemError = false
        val notification = NotificationEntity(
            "title1", "body1", "subtext1",
            "image1", "link1", "date1", "test"
        )
        // when
        val result = useCase(notification)
        // then
        assertTrue(result is Result.Success)
    }

    // Non Business Tests (For Developers):
    // These tests represent system errors that ideally should never happen.
    // Instead of displaying as message on screen, log them locally, remotely or crashlytics etc.

    @Test
    fun `should return system error when notification is not added`() = scope.runTest {
        // given
        (repository as FakeNotificationRepository).isSystemError = true
        val notification = NotificationEntity(
            "title1", "body1", "subtext1",
            "image1", "link1", "date1", "test"
        )
        // when
        val result = useCase(notification)
        // then
        assertTrue(result is Result.Error)
    }
}