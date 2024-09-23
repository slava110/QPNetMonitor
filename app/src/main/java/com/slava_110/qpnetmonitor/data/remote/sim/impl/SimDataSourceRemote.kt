package com.slava_110.qpnetmonitor.data.remote.sim.impl

import com.slava_110.qpnetmonitor.data.remote.sim.SimDataSource
import com.slava_110.qpnetmonitor.data.repository.LoginRepository
import com.slava_110.qpnetmonitor.ext.consumeIncomingDeserializedAsFlow
import com.slava_110.qpnetmonitor.model.dto.SimStepData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.websocket.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SimDataSourceRemote(
    private val loginRepository: LoginRepository,
    private val httpClient: HttpClient
): SimDataSource {
    private var ws: DefaultWebSocketSession? = null

    override suspend fun listModels(): Result<List<String>> =
        httpClient.get("localhost:6851/models") {
            header("token", loginRepository.user?.token)
        }.body<Result<List<String>>>()

    override suspend fun connect(model: String): Result<Flow<SimStepData>> {
        try {
            return Result.success(
                callbackFlow {
                    ws = httpClient.webSocketSession("localhost:6851/ws/$model") {
                        header("token", loginRepository.user?.token)
                    }

                    launch {
                        (ws as DefaultClientWebSocketSession).consumeIncomingDeserializedAsFlow<SimStepData>()
                            .collectLatest {
                                channel.send(it)
                            }
                    }

                    awaitClose {
                        ws?.cancel()
                        channel.close()
                    }
                }
            )
        } catch (e: Throwable) {
            return Result.failure(e)
        }
    }

    override suspend fun sendAction(action: Any): Boolean {
        ws?.send(action.toString())
        return true
    }
}
