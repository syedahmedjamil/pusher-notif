package com.github.syedahmedjamil.pushernotif.domain

data class NotificationEntity(
    val title: String,
    val body: String,
    val subText: String,
    val image: String,
    val link: String,
    val date: String,
    val interest: String
)