package com.slava_110.qpnetmonitor.data.remote.login

import com.slava_110.qpnetmonitor.data.model.LoggedInUser

interface LoginDataSource {

    suspend fun login(username: String, password: String): Result<LoggedInUser>

    suspend fun logout()
}