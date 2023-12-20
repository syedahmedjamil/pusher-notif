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
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class InstanceViewModel(
    private val addInterestUseCase: AddInterestUseCase,
    private val getInterestsUseCase: GetInterestsUseCase
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    val interests = getInterestsUseCase().asLiveData()

    fun addInterest(interest: String) {
        viewModelScope.launch {
            val result = addInterestUseCase(interest)
            if (result is Result.Error) {
                displayError(result.exception.message)
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
        private val getInterestsUseCase: GetInterestsUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return InstanceViewModel(addInterestUseCase, getInterestsUseCase) as T
        }
    }
}