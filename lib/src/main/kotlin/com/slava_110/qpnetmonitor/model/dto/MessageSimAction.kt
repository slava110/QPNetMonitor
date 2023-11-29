package com.slava_110.qpnetmonitor.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class MessageSimAction(
    val type: Type
) {

    enum class Type {
        START,
        STOP,
        STEP,
        RESET
    }
}