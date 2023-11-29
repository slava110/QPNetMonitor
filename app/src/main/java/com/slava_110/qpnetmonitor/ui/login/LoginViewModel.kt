package com.slava_110.qpnetmonitor.ui.login

import androidx.lifecycle.ViewModel
import com.slava_110.qpnetmonitor.debug.AuthRepositoryDebug
import com.slava_110.qpnetmonitor.model.dto.AuthResponse

class LoginViewModel(
    private val authRepo: AuthRepositoryDebug
): ViewModel() {

    suspend fun authorize(login: String, password: String): AuthResponse =
        authRepo.authorize(login, password)
}