package com.slava_110.qpnetmonitor.ui.login.data

import com.slava_110.qpnetmonitor.ui.login.data.model.LoginResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthDataSource: KoinComponent {
    private val client by inject<HttpClient>()

    suspend fun authorize(login: String, password: String): LoginResult =
        client.get("localhost:6851/authorize") {
            parameter("", "")
        }.body<LoginResult>()
}