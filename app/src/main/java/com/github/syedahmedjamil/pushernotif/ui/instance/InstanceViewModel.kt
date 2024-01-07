package com.github.syedahmedjamil.pushernotif.ui.instance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.syedahmedjamil.pushernotif.core.Result
import com.github.syedahmedjamil.pushernotif.usecases.AddInterestUseCase
import com.github.syedahmedjamil.pushernotif.usecases.GetInterestsUseCase
import com.github.syedahmedjamil.pushernotif.usecases.RemoveInterestUseCase
import com.github.syedahmedjamil.pushernotif.usecases.SubscribeUseCase
import com.github.syedahmedjamil.pushernotif.util.Event
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class InstanceViewModel(
    private val addInterestUseCase: AddInterestUseCase,
    private val getInterestsUseCase: GetInterestsUseCase,
    private val removeInterestUseCase: RemoveInterestUseCase,
    private val subscribeUseCase: SubscribeUseCase
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _subscribeEvent = MutableLiveData<Event<Unit>>()
    val subscribeEvent: LiveData<Event<Unit>> = _subscribeEvent

    val interests = getInterestsUseCase().asLiveData()

    fun addInterest(interest: String) {
        viewModelScope.launch {
            val result = addInterestUseCase(interest)
            if (result is Result.Error) {
                displayError(result.exception.message)
            }
        }
    }

    fun removeInterest(interest: String) {
        viewModelScope.launch {
            val result = removeInterestUseCase(interest)
            if (result is Result.Error) {
                displayError(result.exception.message)
            }
        }
    }

    fun subscribe(instanceId: String) {
        viewModelScope.launch {
            val interests = interests.value ?: emptyList()
            val result = subscribeUseCase(instanceId, interests)
            if (result is Result.Error) {
                displayError(result.exception.message)
            }
            if (result is Result.Success) {
                _subscribeEvent.value = Event(Unit)
            }
        }
    }

    private fun displayError(message: String?) {
        message?.let {
            _errorMessage.value = it
        }
    }

    class InstanceViewModelFactory(
        private val addInterestUseCase: AddInterestUseCase,
        private val getInterestsUseCase: GetInterestsUseCase,
        private val removeInterestUseCase: RemoveInterestUseCase,
        private val subscribeUseCase: SubscribeUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return InstanceViewModel(
                addInterestUseCase,
                getInterestsUseCase,
                removeInterestUseCase,
                subscribeUseCase
            ) as T
        }
    }
}