package com.slava_110.qpnetmonitor.data.repository.impl

import com.slava_110.qpnetmonitor.data.local.UserCredentialsStore
import com.slava_110.qpnetmonitor.data.model.LoggedInUser
import com.slava_110.qpnetmonitor.data.remote.login.LoginDataSource
import com.slava_110.qpnetmonitor.data.repository.LoginRepository

class LoginRepositoryImpl(
    private val ds: LoginDataSource,
    private val cs: UserCredentialsStore
): LoginRepository {
    override var user: LoggedInUser? = null
        private set

    override val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    override suspend fun login(): Result<LoggedInUser> =
        cs.retrieveCreds()
            ?.let {
                ds.login(it.first, it.second).getOrNull()
            }
            ?.also { user = it }
            ?.let { Result.success(it) }
            ?: Result.failure(RuntimeException("No credentials!"))

    override suspend fun login(username: String, password: String): Result<LoggedInUser> =
        ds.login(username, password)
            .onSuccess {
                setLoggedInUser(username, password, it)
            }

    private fun setLoggedInUser(username: String, password: String, loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        cs.putCreds(username, password)
    }

    override suspend fun logout() {
        user = null
        cs.clearCreds()
        ds.logout()
    }
}