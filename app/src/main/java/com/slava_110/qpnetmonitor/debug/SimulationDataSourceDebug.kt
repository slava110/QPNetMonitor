package com.slava_110.qpnetmonitor.debug

import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

class SimulationDataSourceDebug: KoinComponent, AutoCloseable {
    private val client by inject<HttpClient>()
    private var wsSession: DefaultClientWebSocketSession? = null

    suspend fun listModels(): List<String> =
        listOf("first", "second")

    suspend fun connect(model: String): Flow<List<Pair<String, Int>>> {
        /*wsSession = client.webSocketSession {

        }*/
        return when(model) {
            "first" -> flow {
                val rand = Random(223521)
                emit(listOf(
                    "Cookies" to rand.nextInt(),
                    "Tickets" to rand.nextInt(),
                    "Balance" to rand.nextInt()
                ))
                delay(2.seconds)
            }
            "second" -> flow {
                val rand = Random(223521)
                emit(listOf(
                    "Cakes" to rand.nextInt(),
                    "Books" to rand.nextInt()
                ))
                delay(2.seconds)
            }

            else -> throw IllegalArgumentException("Unknown model name!")
        }
    }

    suspend fun disconnect() {
        /*wsSession?.let {
            wsSession = null
            it.close()
        }*/
    }

    suspend fun sendAction(action: Any) {
        //wsSession?.sendSerialized(action)
    }


    override fun close() {
        /*client.launch {
            wsSession?.let {
                wsSession = null
                it.close()
            }
        }*/
    }


}