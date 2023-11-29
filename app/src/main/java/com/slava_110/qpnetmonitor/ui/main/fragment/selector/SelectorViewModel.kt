package com.slava_110.qpnetmonitor.ui.main.fragment.selector

import androidx.lifecycle.ViewModel
import com.slava_110.qpnetmonitor.debug.SimulationDataSourceDebug

class SelectorViewModel(private val dataSource: SimulationDataSourceDebug): ViewModel() {

    suspend fun listModels(): List<String> =
        dataSource.listModels()
}