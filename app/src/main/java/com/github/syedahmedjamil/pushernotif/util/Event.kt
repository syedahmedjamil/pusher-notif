package com.github.syedahmedjamil.pushernotif.util

class Event<T>(private val value: T) {
    private var isHandled = false
    fun getValueIfNotHandled(): T? {
        if (isHandled) return null
        else {
            isHandled = true
            return value
        }
    }
    fun getValueRegardless(): T? = value
}