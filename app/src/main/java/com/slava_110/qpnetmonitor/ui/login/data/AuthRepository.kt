package com.slava_110.qpnetmonitor.ui.login.data

import org.koin.core.component.KoinComponent

class AuthRepository(
    var username: String,
    var password: String
): KoinComponent {


    suspend fun authorize(username: String) {

    }
}