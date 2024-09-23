package com.slava_110.qpnetmonitor.data.remote.sim.impl

import android.util.Log
import com.slava_110.qpnetmonitor.data.remote.sim.SimDataSource
import com.slava_110.qpnetmonitor.data.repository.LoginRepository
import com.slava_110.qpnetmonitor.model.dto.MessageSimAction
import com.slava_110.qpnetmonitor.model.dto.SimStepData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

class SimDataSourceDebug(private val loginRepository: LoginRepository): SimDataSource {
    private val simulations = mutableMapOf(
        "first" to DebugSimulation(
            12426,
            listOf("Water", "Employees")
        ),
        "second" to DebugSimulation(
            223521,
            listOf("Cakes", "Books"),
            false
        )
    )

    override suspend fun listModels(): Result<List<String>> =
        Result.success(
            if(loginRepository.user?.isGuest == true)
                listOf(
                    "first"
                )
            else
                listOf(
                    "first",
                    "second"
                )
        )

    override suspend fun connect(model: String): Result<Flow<SimStepData>> =
        simulations[model]?.flow?.let { Result.success(it) }
            ?: Result.failure(IllegalArgumentException("Unknown model name!"))

    override suspend fun sendAction(action: Any): Boolean {
        if(action is MessageSimAction) {
            Log.d("SimData", "received action $action")
            val sim = simulations[action.simName] ?: return false

            when(action.type) {
                MessageSimAction.Type.START -> sim.enabled = true
                MessageSimAction.Type.STOP -> sim.enabled = false
                MessageSimAction.Type.STEP -> sim.step()
                MessageSimAction.Type.RESET -> sim.enabled = false
            }
        }
        return true
    }


    class DebugSimulation(
        seed: Int,
        names: List<String>,
        var enabled: Boolean = true
    ) {
        private var step = false
        private var reset = false
        val flow = flow {
            var rand = Random(seed)

            while(true) {
                if(reset)
                    rand = Random(seed)
                if(enabled || step) {
                    emit(SimStepData(
                        names.associateWith { rand.nextLong(0, Long.MAX_VALUE).toULong() }
                    ))
                    if(step)
                        step = false
                }
                delay(1.seconds)
            }
        }

        fun step() {
            step = true
        }
    }
}