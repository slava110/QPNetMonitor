package com.slava_110.qpnetmonitor.data.repository.impl

import com.slava_110.qpnetmonitor.data.remote.sim.SimDataSource
import com.slava_110.qpnetmonitor.data.repository.SimRepository
import com.slava_110.qpnetmonitor.model.dto.MessageSimAction
import com.slava_110.qpnetmonitor.model.dto.SimStepData
import kotlinx.coroutines.flow.Flow

class SimRepositoryImpl(private val ds: SimDataSource): SimRepository {
    override var modelName: String? = null
        private set
    override var modelFlow: Flow<SimStepData>? = null
        private set

    override suspend fun listModels(): Result<List<String>> =
        ds.listModels()

    override suspend fun connect(model: String): Result<Flow<SimStepData>> {
        val result = ds.connect(model)

        result.onSuccess {
            modelName = model
            modelFlow = it
        }

        return result
    }

    override suspend fun disconnect() {}

    override suspend fun start() {
        modelName?.let { ds.sendAction(MessageSimAction(it, MessageSimAction.Type.START)) }
    }

    override suspend fun stop() {
        modelName?.let { ds.sendAction(MessageSimAction(it, MessageSimAction.Type.STOP)) }
    }
}