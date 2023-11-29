package com.slava_110.qpnetmonitor.ext

import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.*
import io.ktor.util.reflect.*
import io.ktor.websocket.*
import io.ktor.websocket.serialization.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import java.nio.charset.Charset

inline fun <reified T> DefaultClientWebSocketSession.consumeIncomingDeserializedAsFlow(): Flow<T> =
    consumeIncomingDeserializedAsFlow(typeInfo<T>())

@Suppress("UNCHECKED_CAST")
fun <T> DefaultClientWebSocketSession.consumeIncomingDeserializedAsFlow(
    typeInfo: TypeInfo
): Flow<T> {
    val converter = converter
        ?: throw WebsocketConverterNotFoundException("No converter was found for websocket")

    @Suppress("UNCHECKED_CAST")
    return consumeIncomingDeserializedAsFlow(
        typeInfo,
        converter,
        call.request.headers.suitableCharset()
    ) as Flow<T>
}

private fun WebSocketSession.consumeIncomingDeserializedAsFlow(
    typeInfo: TypeInfo,
    converter: WebsocketContentConverter,
    charset: Charset
): Flow<Any?> = incoming.consumeAsFlow().map { frame ->
    if (!converter.isApplicable(frame)) {
        throw WebsocketDeserializeException(
            "Converter doesn't support frame type ${frame.frameType.name}",
            frame = frame
        )
    }

    val result = converter.deserialize(
        charset = charset,
        typeInfo = typeInfo,
        content = frame
    )

    when {
        typeInfo.type.isInstance(result) -> return@map result
        result == null -> {
            if (typeInfo.kotlinType?.isMarkedNullable == true) return@map null
            throw WebsocketDeserializeException("Frame has null content", frame = frame)
        }
    }

    throw WebsocketDeserializeException(
        "Can't deserialize value: expected value of type ${typeInfo.type.simpleName}," +
                " got ${result!!::class.simpleName}",
        frame = frame
    )
}