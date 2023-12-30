package com.github.syedahmedjamil.pushernotif.data

import com.github.syedahmedjamil.pushernotif.domain.InterestRepository
import com.github.syedahmedjamil.pushernotif.shared_test.FakeInterestLocalDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class InterestRepositoryTest {

    private lateinit var interestRepository: InterestRepository
    private lateinit var fakeInterestLocalDataSource: FakeInterestLocalDataSource

    @Before
    fun setUp() {
        fakeInterestLocalDataSource = FakeInterestLocalDataSource(
            mutableListOf("interest1", "interest2", "interest3")
        )
        interestRepository = InterestRepositoryImpl(fakeInterestLocalDataSource)
    }

    @Test
    fun `should add interest`() = runTest {
        // given
        val interest = "interest"
        // when
        interestRepository.addInterest(interest)
        // then
        assertTrue(fakeInterestLocalDataSource.isAddInterestCalled)
    }

    @Test
    fun `should get interests`() = runTest {
        // given
        val expected = listOf("interest1", "interest2", "interest3")
        // when
        val actual = interestRepository.getInterests().first()
        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should remove interest`() = runTest {
        // given
        val interest = "interest"
        // when
        interestRepository.removeInterest(interest)
        // then
        assertTrue(fakeInterestLocalDataSource.isRemoveInterestCalled)
    }
}