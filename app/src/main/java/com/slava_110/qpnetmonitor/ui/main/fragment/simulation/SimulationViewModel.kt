package com.slava_110.qpnetmonitor.ui.main.fragment.simulation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slava_110.qpnetmonitor.data.model.LoggedInUser
import com.slava_110.qpnetmonitor.data.repository.SimRepository
import com.slava_110.qpnetmonitor.model.dto.SimStepData
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SimulationViewModel(
    private val repo: SimRepository
): ViewModel() {
    private val _steps = MutableSharedFlow<SimStepData>()
    val steps: SharedFlow<SimStepData> =
        _steps.asSharedFlow()
    private var job: Job? = null

    fun connect(modelName: String) {
        job?.cancel()
        job = viewModelScope.launch {
            val response = repo.connect(modelName)
            response.onSuccess { flow ->
                flow.collect {
                    _steps.emit(it)
                }
            }
        }
    }

    override fun onCleared() {
        job?.cancel()
    }

    fun start() {
        viewModelScope.launch {
            repo.start()
        }
    }

    fun stop() {
        viewModelScope.launch {
            repo.stop()
        }
    }
}