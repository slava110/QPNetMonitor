package com.slava_110.qpnetmonitor.ui.main.fragment.selector

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slava_110.qpnetmonitor.data.repository.SimRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SelectorViewModel(private val repo: SimRepository): ViewModel() {
    private val _modelNames = MutableSharedFlow<Result<List<String>>>()
    val modelNames: SharedFlow<Result<List<String>>> =
        _modelNames.asSharedFlow()

    fun updateModelList() {
        viewModelScope.launch {
            val list = repo.listModels()
            _modelNames.emit(list)
        }
    }
}