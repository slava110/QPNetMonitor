package com.slava_110.qpnetmonitor.debug

import com.slava_110.qpnetmonitor.model.dto.AuthResponse
import com.slava_110.qpnetmonitor.ui.login.data.model.LoginResult

class AuthRepositoryDebug {
    var credentials: Pair<String, String>? = null

    suspend fun authorize(login: String, password: String): AuthResponse {
        if(login == "slava@bk.ru" && password == "12345") {
            credentials = login to password
            return AuthResponse.Success("Slava", false)
        } else if(login == "admin@bk.ru" && password == "12345") {
            credentials = login to password
            return AuthResponse.Success("Admin", true)
        }
        credentials = null
        return AuthResponse.Failure("Ошибка авторизации")
    }

    suspend fun logout() {
        credentials = null
    }
}