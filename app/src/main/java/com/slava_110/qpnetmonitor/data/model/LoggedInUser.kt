package com.slava_110.qpnetmonitor.data.model

data class LoggedInUser(
    val displayName: String,
    val isGuest: Boolean,
    val token: String
)