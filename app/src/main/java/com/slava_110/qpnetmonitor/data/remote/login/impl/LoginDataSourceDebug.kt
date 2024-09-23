package com.slava_110.qpnetmonitor.data.remote.login.impl

import com.slava_110.qpnetmonitor.data.model.LoggedInUser
import com.slava_110.qpnetmonitor.data.remote.login.LoginDataSource
import java.io.IOException

class LoginDataSourceDebug: LoginDataSource {

    override suspend fun login(username: String, password: String): Result<LoggedInUser> {
        if(username == "slava@bk.ru" && password == "12345") {
            return Result.success(LoggedInUser("Slava", true, ""))
        } else if(username == "admin@bk.ru" && password == "12345") {
            return Result.success(LoggedInUser("Admin", false, ""))
        } else if(username == "1" && password == "1") {
            return Result.success(LoggedInUser("Tester", true, ""))
        }
        return Result.failure(IOException("Invalid credentials"))
    }

    override suspend fun logout() {}

}