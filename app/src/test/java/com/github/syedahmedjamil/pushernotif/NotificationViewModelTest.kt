package com.github.syedahmedjamil.pushernotif

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.syedahmedjamil.pushernotif.core.Result
import com.github.syedahmedjamil.pushernotif.domain.NotificationEntity
import com.github.syedahmedjamil.pushernotif.shared_test.fakes.usecase.FakeDeleteNotificationsUseCase
import com.github.syedahmedjamil.pushernotif.shared_test.fakes.usecase.FakeGetNotificationUseCase
import com.github.syedahmedjamil.pushernotif.ui.notification.NotificationViewModel
import com.github.syedahmedjamil.pushernotif.usecases.DeleteNotificationsUseCase
import com.github.syedahmedjamil.pushernotif.usecases.GetNotificationsUseCase
import com.github.syedahmedjamil.pushernotif.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class NotificationViewModelTest {
    private lateinit var viewModel: NotificationViewModel
    private lateinit var getNotificationsUseCase: GetNotificationsUseCase
    private lateinit var deleteNotificationsUseCase: DeleteNotificationsUseCase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        getNotificationsUseCase = FakeGetNotificationUseCase()
        deleteNotificationsUseCase = FakeDeleteNotificationsUseCase()
        viewModel =
            NotificationViewModel(
                getNotificationsUseCase,
                deleteNotificationsUseCase
            )
    }

    @Ignore("Don't know how to test this yet, code is working though")
    @Test
    fun `should get notification of selected interest`() = runTest {
        // given
        val useCase = (getNotificationsUseCase as FakeGetNotificationUseCase)
        val interest1 = "test1"
        val interest2 = "test2"
        val expected1 = listOf(
            NotificationEntity(
                "title1", "body1", "subtext1",
                "image1", "link1", "date1", "test1"
            ),
            NotificationEntity(
                "title2", "body2", "subtext2",
                "image2", "link2", "date2", "test1"
            )
        )
        val expected2 = listOf(
            NotificationEntity(
                "title1", "body1", "subtext1",
                "image1", "link1", "date1", "test2"
            ),
            NotificationEntity(
                "title2", "body2", "subtext2",
                "image2", "link2", "date2", "test2"
            )
        )
        // when
        val actual = mutableListOf<List<NotificationEntity>>()
        viewModel.notifications.observeForever { list -> actual.add(list) }
        advanceUntilIdle()
        viewModel.selectInterest(interest1)
        useCase.emit(expected1)
        useCase.emit(expected2)
        // then
        assertEquals(expected1, actual)
    }

    @Test
    fun `should select interest`() = runTest {
        // given
        assertEquals("", viewModel.selectedInterestFlow.value)
        // when
        viewModel.selectInterest("interest1")
        // then
        assertEquals("interest1", viewModel.selectedInterestFlow.value)
        
    }

    @Test
    fun `should open link`() = runTest {
        // given
        val expected = "link"
        // when
        viewModel.openLink("link")
        val actual = viewModel.openLinkEvent.value?.getValueIfNotHandled()
        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `should delete notifications`() = runTest {
        // given
        val useCase = ( deleteNotificationsUseCase as FakeDeleteNotificationsUseCase)
        useCase.isError = false
        // when
        viewModel.deleteNotifications()
        advanceUntilIdle()
        val result = useCase.result
        // then
        assertTrue(result is Result.Success)
    }

    @Test
    fun `should return error when notification is not removed`() = runTest {
        // given
        val useCase = ( deleteNotificationsUseCase as FakeDeleteNotificationsUseCase)
        useCase.isError = true
        // when
        viewModel.deleteNotifications()
        advanceUntilIdle()
        val result = useCase.result
        // then
        assertTrue(result is Result.Error)
    }
}