package com.slava_110.qpnetmonitor.data.repository

import com.slava_110.qpnetmonitor.model.dto.SimStepData
import kotlinx.coroutines.flow.Flow

interface SimRepository {
    val modelName: String?
    val modelFlow: Flow<SimStepData>?

    suspend fun listModels(): Result<List<String>>

    suspend fun connect(model: String): Result<Flow<SimStepData>>

    suspend fun disconnect()

    suspend fun start()

    suspend fun stop()
}