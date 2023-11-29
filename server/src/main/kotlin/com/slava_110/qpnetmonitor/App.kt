package com.slava_110.qpnetmonitor

import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.*
import kotlinx.serialization.cbor.Cbor

fun main() {
    embeddedServer(CIO, port = 6851) {
        routes()
    }.start(wait = true)
}

private fun Application.plugins() {
    install(WebSockets) {
        contentConverter = KotlinxWebsocketSerializationConverter(Cbor {
            encodeDefaults = false
        })
    }
    authentication {
        val myRealm = "MyRealm"
        val usersInMyRealmToHA1: Map<String, ByteArray> = mapOf(
            // pass="test", HA1=MD5("test:MyRealm:pass")="fb12475e62dedc5c2744d98eb73b8877"
            "test" to hex("fb12475e62dedc5c2744d98eb73b8877")
        )

        digest("myDigestAuth") {
            digestProvider { userName, realm ->
                usersInMyRealmToHA1[userName]
            }
        }
    }
}

private fun Application.routes() {
    routing {

    }
}