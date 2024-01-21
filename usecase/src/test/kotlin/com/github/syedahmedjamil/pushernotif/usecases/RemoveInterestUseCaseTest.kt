package com.github.syedahmedjamil.pushernotif.usecases

import com.github.syedahmedjamil.pushernotif.core.NonBusinessException
import com.github.syedahmedjamil.pushernotif.core.Result
import com.github.syedahmedjamil.pushernotif.domain.InterestRepository
import com.github.syedahmedjamil.pushernotif.shared_test.fakes.repository.FakeInterestRepository
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RemoveInterestUseCaseTest {
    private lateinit var useCase: RemoveInterestUseCase
    private lateinit var repository: InterestRepository
    private val testDispatcher = StandardTestDispatcher()
    private val scope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        repository = FakeInterestRepository()
        useCase = RemoveInterestUseCaseImpl(repository, testDispatcher)
    }

    // Business Tests (For Users)
    // These tests contribute to the features of the app.

    @Test
    fun `should return success when interest is removed`() = scope.runTest {
        // given
        (repository as FakeInterestRepository).isSystemError = false
        val interest = "interest"
        // when
        val result = useCase(interest)
        // then
        assertTrue(result is Result.Success)
    }

    // Non Business Tests (For Developers):
    // These tests represent system errors that ideally should never happen.
    // Instead of displaying as message on screen, log them locally, remotely or crashlytics etc.

    @Test
    fun `should return system error when interest is not removed`() = scope.runTest {
        // given
        (repository as FakeInterestRepository).isSystemError = true
        val interest = "interest"
        val expected = "NonBusinessError: error removing interest."
        // when
        val result = useCase(interest)
        // then
        assertTrue(result is Result.Error)
        result as Result.Error
        assertTrue(result.exception is NonBusinessException)
        val actual = result.exception.message
        assertEquals(expected, actual)
    }
}