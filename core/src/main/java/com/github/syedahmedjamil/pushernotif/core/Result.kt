package com.github.syedahmedjamil.pushernotif.core

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}


abstract class BusinessException(message: String?) : Exception(message)
class EmptyInterestException(message: String? = null) : BusinessException(message)
class DuplicateInterestException(message: String? = null) : BusinessException(message)
class EmptyInstanceIdException(message: String? = null) : BusinessException(message)
class NoInterestAddedException(message: String? = null) : BusinessException(message)
class NoNetworkException(message: String? = null) : BusinessException(message)

class NonBusinessException(message: String? = null) : Exception(message)
