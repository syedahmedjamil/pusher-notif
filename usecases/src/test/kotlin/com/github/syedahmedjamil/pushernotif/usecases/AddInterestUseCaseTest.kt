package com.github.syedahmedjamil.pushernotif.usecases

import com.github.syedahmedjamil.pushernotif.core.BusinessException
import com.github.syedahmedjamil.pushernotif.core.DuplicateInterestException
import com.github.syedahmedjamil.pushernotif.core.EmptyInterestException
import com.github.syedahmedjamil.pushernotif.core.NonBusinessException
import com.github.syedahmedjamil.pushernotif.core.Result
import com.github.syedahmedjamil.pushernotif.shared_test.FakeInterestRepository
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AddInterestUseCaseTest {
    private lateinit var useCase: AddInterestUseCase
    private lateinit var repository: FakeInterestRepository
    private val testDispatcher = StandardTestDispatcher()
    private val scope = TestScope(testDispatcher)


    @Before
    fun setUp() {
        repository = FakeInterestRepository()
        useCase = AddInterestUseCaseImpl(repository, testDispatcher)
    }

    @Test
    fun `should return success when interest is added`() = scope.runTest {
        // given
        repository.isSystemError = false
        val interest = "interest"
        // when
        val result = useCase(interest)
        // then
        assertTrue(result is Result.Success)
    }

    @Test
    fun `should return non-business error when interest is not added`() = scope.runTest {
        // given
        repository.isSystemError = true
        val interest = "interest"
        val expected = "NonBusinessError: error adding interest."
        // when
        val result = useCase(interest)
        // then
        assertTrue(result is Result.Error)
        result as Result.Error
        assertTrue(result.exception is NonBusinessException)
        val actual = result.exception.message
        assertEquals(expected, actual)
    }

    @Test
    fun `should return business error when duplicate interest is added`() = scope.runTest {
        // given
        repository.isSystemError = false
        val interest = "interest"
        val expected = "Interest already exists."
        repository.interests.add(interest)
        // when
        useCase(interest)
        val result = useCase(interest)
        // then
        assertTrue(result is Result.Error)
        result as Result.Error
        assertTrue(result.exception is BusinessException)
        assertTrue(result.exception is DuplicateInterestException)
        val actual = result.exception.message
        assertEquals(expected, actual)
    }

    @Test
    fun `should return business error when empty interest is added`() = scope.runTest {
        // given
        repository.isSystemError = false
        val interest = ""
        val expected = "Interest can't be empty."
        repository.interests.add(interest)
        // when
        useCase(interest)
        val result = useCase(interest)
        // then
        assertTrue(result is Result.Error)
        result as Result.Error
        assertTrue(result.exception is BusinessException)
        assertTrue(result.exception is EmptyInterestException)
        val actual = result.exception.message
        assertEquals(expected, actual)
    }


}