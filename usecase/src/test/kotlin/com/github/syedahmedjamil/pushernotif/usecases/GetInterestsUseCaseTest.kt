package com.github.syedahmedjamil.pushernotif.usecases

import com.github.syedahmedjamil.pushernotif.domain.InterestRepository
import com.github.syedahmedjamil.pushernotif.shared_test.fakes.repository.FakeInterestRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetInterestsUseCaseTest {

    private lateinit var useCase: GetInterestsUseCase
    private lateinit var repository: InterestRepository

    @Before
    fun setUp() {
        repository = FakeInterestRepository(
            mutableListOf(
                "interest1",
                "interest2",
                "interest3"
            )
        )
        useCase = GetInterestsUseCaseImpl(repository)
    }

    // Business Tests (For Users)
    // These tests contribute to the features of the app.

    @Test
    fun `should return list of interests`() = runTest {
        // given
        val expected = listOf("interest1", "interest2", "interest3")
        // when
        val actual = useCase().first()
        // then
        assertEquals(expected, actual)
    }
}