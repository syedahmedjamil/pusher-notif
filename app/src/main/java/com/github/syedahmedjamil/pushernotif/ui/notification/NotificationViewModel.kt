package com.github.syedahmedjamil.pushernotif.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.syedahmedjamil.pushernotif.usecases.DeleteNotificationsUseCase
import com.github.syedahmedjamil.pushernotif.usecases.GetNotificationsUseCase
import com.github.syedahmedjamil.pushernotif.util.Event
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class NotificationViewModel(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val deleteNotificationsUseCase: DeleteNotificationsUseCase
) : ViewModel() {

    private val _openLink = MutableLiveData<Event<String>>()
    val openLinkEvent: LiveData<Event<String>> = _openLink

    val selectedInterestFlow = MutableStateFlow<String>("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val notifications = selectedInterestFlow.flatMapLatest {
        getNotificationsUseCase(it)
    }.asLiveData()

    fun selectInterest(interest: String) {
        selectedInterestFlow.value = interest
    }

    fun deleteNotifications() {
        viewModelScope.launch {
            deleteNotificationsUseCase()
        }
    }


    fun openLink(link: String?) {
        link?.let {
            _openLink.value = Event(it)
        }
    }

    class NotificationViewModelFactory(
        private val getNotificationsUseCase: GetNotificationsUseCase,
        private val deleteNotificationsUseCase: DeleteNotificationsUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NotificationViewModel(
                getNotificationsUseCase,
                deleteNotificationsUseCase
            ) as T
        }
    }
}