package com.github.syedahmedjamil.pushernotif.usecases

import com.github.syedahmedjamil.pushernotif.core.NonBusinessException
import com.github.syedahmedjamil.pushernotif.core.Result
import com.github.syedahmedjamil.pushernotif.domain.NotificationRepository
import com.github.syedahmedjamil.pushernotif.shared_test.fakes.repository.FakeNotificationRepository
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DeleteNotificationsUseCaseTest {
    private lateinit var useCase: DeleteNotificationsUseCase
    private lateinit var repository: NotificationRepository
    private val testDispatcher = StandardTestDispatcher()
    private val scope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        repository = FakeNotificationRepository()
        useCase = DeleteNotificationsUseCaseImpl(repository, testDispatcher)
    }

    // Business Tests (For Users)
    // These tests contribute to the features of the app.

    @Test
    fun `should return success when notifications are deleted`() = scope.runTest {
        // given
        (repository as FakeNotificationRepository).isSystemError = false
        // when
        val result = useCase()
        // then
        assertTrue(result is Result.Success)
    }

    // Non Business Tests (For Developers):
    // These tests represent system errors that ideally should never happen.
    // Instead of displaying as message on screen, log them locally, remotely or crashlytics etc.

    @Test
    fun `should return system error when interest is not removed`() = scope.runTest {
        // given
        (repository as FakeNotificationRepository).isSystemError = true
        val expected = "NonBusinessError: error deleting notifications."
        // when
        val result = useCase()
        // then
        assertTrue(result is Result.Error)
        result as Result.Error
        assertTrue(result.exception is NonBusinessException)
        val actual = result.exception.message
        assertEquals(expected, actual)
    }
}