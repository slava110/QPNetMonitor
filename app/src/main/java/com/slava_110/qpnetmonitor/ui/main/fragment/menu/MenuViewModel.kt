package com.slava_110.qpnetmonitor.ui.main.fragment.menu

import androidx.lifecycle.ViewModel
import com.slava_110.qpnetmonitor.data.repository.LoginRepository

class MenuViewModel(
    private val loginRepository: LoginRepository
): ViewModel() {

    suspend fun logout() {
        loginRepository.logout()
    }
}