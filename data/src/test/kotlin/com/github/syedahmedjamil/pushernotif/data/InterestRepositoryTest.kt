package com.github.syedahmedjamil.pushernotif.data

import com.github.syedahmedjamil.pushernotif.domain.InterestDataSource
import com.github.syedahmedjamil.pushernotif.domain.InterestRepository
import com.github.syedahmedjamil.pushernotif.shared_test.fakes.source.FakeInterestLocalDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class InterestRepositoryTest {

    private lateinit var repository: InterestRepository
    private lateinit var dataSource: InterestDataSource

    @Before
    fun setUp() {
        dataSource = FakeInterestLocalDataSource(
            mutableListOf("interest1", "interest2", "interest3")
        )
        repository = InterestRepositoryImpl(dataSource)
    }

    @Test
    fun `should add interest`() = runTest {
        // given
        val interest = "interest"
        // when
        repository.addInterest(interest)
        // then
        assertTrue((dataSource as FakeInterestLocalDataSource).isAddInterestCalled)
    }

    @Test
    fun `should get interests`() = runTest {
        // given
        val expected = listOf("interest1", "interest2", "interest3")
        // when
        val actual = repository.getInterests().first()
        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should remove interest`() = runTest {
        // given
        val interest = "interest"
        // when
        repository.removeInterest(interest)
        // then
        assertTrue((dataSource as FakeInterestLocalDataSource).isRemoveInterestCalled)
    }
}