package com.slava_110.qpnetmonitor.ui.login.data.model

sealed class LoginResult {
    class Success(val displayName: String): LoginResult()
    class Errored(val error: String): LoginResult()
}