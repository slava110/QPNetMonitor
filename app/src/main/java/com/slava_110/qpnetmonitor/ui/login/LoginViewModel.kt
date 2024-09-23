package com.slava_110.qpnetmonitor.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slava_110.qpnetmonitor.data.model.LoggedInUser
import com.slava_110.qpnetmonitor.data.repository.LoginRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepo: LoginRepository
): ViewModel() {
    private val _authResult = MutableSharedFlow<Result<LoggedInUser>>()
    val authResult: SharedFlow<Result<LoggedInUser>> =
        _authResult.asSharedFlow()

    fun authorizeSaved() {
        viewModelScope.launch {
            authRepo.login().onSuccess {
                _authResult.emit(Result.success(it))
            }
        }
    }

    fun authorize(login: String, password: String) {
        viewModelScope.launch {
            val response = authRepo.login(login, password)
            _authResult.emit(response)
        }
    }
}