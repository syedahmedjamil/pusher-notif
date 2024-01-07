package com.github.syedahmedjamil.pushernotif

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.syedahmedjamil.pushernotif.core.Result
import com.github.syedahmedjamil.pushernotif.shared_test.fakes.service.FakeSubscribeService
import com.github.syedahmedjamil.pushernotif.shared_test.fakes.usecase.FakeAddInterestUseCase
import com.github.syedahmedjamil.pushernotif.shared_test.fakes.usecase.FakeGetInterestsUseCase
import com.github.syedahmedjamil.pushernotif.shared_test.fakes.usecase.FakeRemoveInterestUseCase
import com.github.syedahmedjamil.pushernotif.shared_test.fakes.usecase.FakeSubscribeUseCase
import com.github.syedahmedjamil.pushernotif.ui.instance.InstanceViewModel
import com.github.syedahmedjamil.pushernotif.usecases.AddInterestUseCase
import com.github.syedahmedjamil.pushernotif.usecases.GetInterestsUseCase
import com.github.syedahmedjamil.pushernotif.usecases.RemoveInterestUseCase
import com.github.syedahmedjamil.pushernotif.usecases.SubscribeUseCase
import com.github.syedahmedjamil.pushernotif.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class InstanceViewModelTest {
    private lateinit var viewModel: InstanceViewModel
    private lateinit var addInterestUseCase: AddInterestUseCase
    private lateinit var getInterestsUseCase: GetInterestsUseCase
    private lateinit var removeInterestUseCase: RemoveInterestUseCase
    private lateinit var subscribeUseCase: SubscribeUseCase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        addInterestUseCase = FakeAddInterestUseCase()
        getInterestsUseCase = FakeGetInterestsUseCase()
        removeInterestUseCase = FakeRemoveInterestUseCase()
        subscribeUseCase = FakeSubscribeUseCase()
        viewModel =
            InstanceViewModel(
                addInterestUseCase,
                getInterestsUseCase,
                removeInterestUseCase,
                subscribeUseCase
            )
    }

    // Business Tests (For Users)
    // These tests contribute to the features of the app.
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should add interest`() = runTest {
        // given
        val useCase = (addInterestUseCase as FakeAddInterestUseCase)
        useCase.isError = false
        val interest = "interest"
        // when
        viewModel.addInterest(interest)
        advanceUntilIdle()
        val result = useCase.result
        // then
        assertTrue(result is Result.Success)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should get interests`() = runTest {
        // given
        val useCase = (getInterestsUseCase as FakeGetInterestsUseCase)
        val expected1 = emptyList<String>()
        val expected2 = listOf("interest1", "interest2", "interest3")
        // when
        val actual = mutableListOf<List<String>>()
        viewModel.interests.observeForever { list -> actual.add(list) }
        advanceUntilIdle()
        useCase.emit(expected1)
        useCase.emit(expected2)
        // then
        assertEquals(expected1, actual[0])
        assertEquals(expected2, actual[1])
        assertEquals(2, actual.size)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should display error when interest is not added`() = runTest {
        // given
        val useCase = (addInterestUseCase as FakeAddInterestUseCase)
        useCase.isError = true
        val interest = "interest"
        val expected = "error"
        // when
        viewModel.addInterest(interest)
        advanceUntilIdle()
        val result = useCase.result
        val actual = viewModel.errorMessage.value
        // then
        assertTrue(result is Result.Error)
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should remove interest`() = runTest {
        // given
        val useCase = (removeInterestUseCase as FakeRemoveInterestUseCase)
        useCase.isError = false
        val interest = "interest"
        // when
        viewModel.removeInterest(interest)
        advanceUntilIdle()
        val result = useCase.result
        // then
        assertTrue(result is Result.Success)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should display error when interest is not removed`() = runTest {
        // given
        val useCase = (removeInterestUseCase as FakeRemoveInterestUseCase)
        useCase.isError = true
        val interest = "interest"
        val expected = "error"
        // when
        viewModel.removeInterest(interest)
        advanceUntilIdle()
        val result = useCase.result
        val actual = viewModel.errorMessage.value
        // then
        assertTrue(result is Result.Error)
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should display error when unable to subscribe`() = runTest {
        // given
        val useCase = (subscribeUseCase as FakeSubscribeUseCase)
        useCase.isError = true
        val instanceId = "00000000-0000-0000-0000-000000000000"
        val interests = listOf("interest1")
        val expected = "error"
        // when
        viewModel.subscribe(instanceId)
        advanceUntilIdle()
        (getInterestsUseCase as FakeGetInterestsUseCase).emit(interests)
        val result = useCase.result
        val actual = viewModel.errorMessage.value
        // then
        assertTrue(result is Result.Error)
        assertEquals(expected, actual)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should navigate to notifications screen when subscribed`() = runTest {
        // given
        val useCase = (subscribeUseCase as FakeSubscribeUseCase)
        useCase.isError = false
        val instanceId = "00000000-0000-0000-0000-000000000000"
        val interests = listOf("interest1")
        val expected = Unit
        // when
        viewModel.subscribe(instanceId)
        advanceUntilIdle()
        (getInterestsUseCase as FakeGetInterestsUseCase).emit(interests)
        val result = useCase.result
        val actual = viewModel.subscribeEvent.value?.getValueIfNotHandled()
        // then
        assertTrue(result is Result.Success)
        assertEquals(expected, actual)
    }

}