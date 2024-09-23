package com.slava_110.qpnetmonitor.data.remote.login.impl

import com.slava_110.qpnetmonitor.data.model.LoggedInUser
import com.slava_110.qpnetmonitor.data.remote.login.LoginDataSource
import com.slava_110.qpnetmonitor.data.repository.LoginRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginDataSourceRemote(private val loginRepository: LoginRepository): LoginDataSource, KoinComponent {
    private val client by inject<HttpClient>()

    override suspend fun login(username: String, password: String): Result<LoggedInUser> =
        client.get("localhost:6851/authorize") {
            parameter("username", username)
            parameter("password", password)
        }.body<Result<LoggedInUser>>()

    override suspend fun logout() {
        client.put("localhost:6851/unauthorize") {
            header("token", loginRepository.user?.token)
        }.body<Result<LoggedInUser>>()
    }
}