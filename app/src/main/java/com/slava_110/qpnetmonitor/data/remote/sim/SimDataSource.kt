package com.slava_110.qpnetmonitor.data.remote.sim

import com.slava_110.qpnetmonitor.model.dto.SimStepData
import kotlinx.coroutines.flow.Flow

interface SimDataSource {

    suspend fun listModels(): Result<List<String>>

    suspend fun connect(model: String): Result<Flow<SimStepData>>

    suspend fun sendAction(action: Any): Boolean
}