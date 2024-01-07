package com.github.syedahmedjamil.pushernotif.usecases

import com.github.syedahmedjamil.pushernotif.core.NonBusinessException
import com.github.syedahmedjamil.pushernotif.core.Result
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
    private lateinit var repository: FakeInterestRepository
    private val testDispatcher = StandardTestDispatcher()
    private val scope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        repository = FakeInterestRepository()
        useCase = RemoveInterestUseCaseImpl(repository, testDispatcher)
    }

    @Test
    fun `should return success when interest is removed`() = scope.runTest {
        // given
        repository.isSystemError = false
        val interest = "interest"
        // when
        val result = useCase(interest)
        // then
        assertTrue(result is Result.Success)
    }

    @Test
    fun `should return non-business error when interest is not removed`() = scope.runTest {
        // given
        repository.isSystemError = true
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