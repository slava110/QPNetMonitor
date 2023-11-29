package com.slava_110.qpnetmonitor

import android.app.Application
import android.util.Log
import com.slava_110.qpnetmonitor.debug.AuthRepositoryDebug
import com.slava_110.qpnetmonitor.ui.login.LoginViewModel
import com.slava_110.qpnetmonitor.ui.main.fragment.selector.SelectorViewModel
import com.slava_110.qpnetmonitor.ui.main.fragment.simulation.SimulationViewModel
import com.slava_110.qpnetmonitor.debug.SimulationDataSourceDebug
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.*
import kotlinx.serialization.cbor.Cbor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.dsl.onClose

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                module {
                    single {
                        HttpClient(CIO) {
                            install(WebSockets) {
                                contentConverter = KotlinxWebsocketSerializationConverter(Cbor {
                                    encodeDefaults = false
                                    ignoreUnknownKeys = true
                                })
                            }
                            install(Auth) {
                                digest {
                                    val repo = get<AuthRepositoryDebug>()
                                    credentials {
                                        repo.credentials?.let { (login, pass) ->
                                            DigestAuthCredentials(login, pass)
                                        }
                                    }
                                }
                            }
                        }
                    } onClose { it?.close() }

                    singleOf(::AuthRepositoryDebug)
                    singleOf(::SimulationDataSourceDebug) onClose { it?.close() }

                    viewModelOf(::SimulationViewModel)
                    viewModelOf(::LoginViewModel)
                    viewModelOf(::SelectorViewModel)
                }
            )
        }

        Log.d("Koin", "Scope created")
    }
}