package com.github.syedahmedjamil.pushernotif.usecases

import com.github.syedahmedjamil.pushernotif.core.BusinessException
import com.github.syedahmedjamil.pushernotif.core.EmptyInstanceIdException
import com.github.syedahmedjamil.pushernotif.core.NoInterestAddedException
import com.github.syedahmedjamil.pushernotif.core.NoNetworkException
import com.github.syedahmedjamil.pushernotif.core.NonBusinessException
import com.github.syedahmedjamil.pushernotif.core.Result
import com.github.syedahmedjamil.pushernotif.domain.SubscribeService
import com.github.syedahmedjamil.pushernotif.shared_test.fakes.service.FakeSubscribeService
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SubscribeUseCaseTest {

    private lateinit var useCase: SubscribeUseCase
    private lateinit var service: SubscribeService
    private val testDispatcher = StandardTestDispatcher()
    private val scope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        service = FakeSubscribeService()
        useCase = SubscribeUseCaseImpl(service, testDispatcher)
    }

    // Business Tests (For Users)
    // These tests contribute to the features of the app.

    @Test
    fun `should return success when connecting with valid data and network`() = scope.runTest {
        // given
        (service as FakeSubscribeService).isNetwork = true
        val instanceId = "00000000-0000-0000-0000-000000000000"
        val interests = listOf("test")
        // when
        val result = useCase(instanceId, interests)
        // then
        assertTrue(result is Result.Success)
    }

    @Test
    fun `should return business error when subscribing with empty instance id`() = scope.runTest {
        // given
        val instanceId = ""
        val interests = emptyList<String>()
        val expected = "Please enter your Pusher Instance ID."
        // when
        val result = useCase(instanceId, interests)
        // then
        assertTrue(result is Result.Error)
        result as Result.Error
        assertTrue(result.exception is BusinessException)
        assertTrue(result.exception is EmptyInstanceIdException)
        val actual = result.exception.message
        assertEquals(expected, actual)
    }

    @Test
    fun `should return business error when connecting with no interests`() = scope.runTest {
        // given
        val instanceId = "00000000-0000-0000-0000-000000000000"
        val interests = emptyList<String>()
        val expected = "Please add at least 1 interest before subscribing."
        // when
        val result = useCase(instanceId, interests)
        // then
        assertTrue(result is Result.Error)
        result as Result.Error
        assertTrue(result.exception is BusinessException)
        assertTrue(result.exception is NoInterestAddedException)
        val actual = result.exception.message
        assertEquals(expected, actual)
    }

    @Test
    fun `should return business error when subscribing with no network`() = scope.runTest {
        // given
        (service as FakeSubscribeService).isNetwork = false
        val instanceId = "00000000-0000-0000-0000-000000000000"
        val interests = listOf("test")
        val expected = "Network unavailable."
        // when
        val result = useCase(instanceId, interests)
        // then
        assertTrue(result is Result.Error)
        result as Result.Error
        assertTrue(result.exception is BusinessException)
        assertTrue(result.exception is NoNetworkException)
        val actual = result.exception.message
        assertEquals(expected, actual)
    }


    // Non Business Tests (For Developers):
    // These tests represent system errors that ideally should never happen.
    // Instead of displaying as message on screen, log them locally, remotely or crashlytics etc.

    @Test
    fun `should return system error when system error`() = scope.runTest {
        // given
        (service as FakeSubscribeService).isSystemError = true
        val instanceId = "00000000-0000-0000-0000-000000000000"
        val interests = listOf("test")
        val expected = "NonBusinessError: subscribe."
        // when
        val result = useCase(instanceId, interests)
        // then
        assertTrue(result is Result.Error)
        result as Result.Error
        assertTrue(result.exception is NonBusinessException)
        val actual = result.exception.message
        assertEquals(expected, actual)
    }
}