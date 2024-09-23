package com.slava_110.qpnetmonitor.data.repository

import com.slava_110.qpnetmonitor.data.model.LoggedInUser

interface LoginRepository {
    val user: LoggedInUser?

    val isLoggedIn: Boolean
        get() = user != null

    suspend fun login(): Result<LoggedInUser>

    suspend fun login(username: String, password: String): Result<LoggedInUser>

    suspend fun logout()
}