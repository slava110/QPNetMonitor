package com.slava_110.qpnetmonitor.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class MessageSimUpdate(
    val elements: List<Long>
)