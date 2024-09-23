package com.slava_110.qpnetmonitor.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class SimStepData(
    val params: Map<String, ULong>
)