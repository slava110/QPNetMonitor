package com.slava_110.qpnetmonitor

import android.app.Application
import android.util.Log
import com.slava_110.qpnetmonitor.data.local.UserCredentialsStore
import com.slava_110.qpnetmonitor.data.remote.login.LoginDataSource
import com.slava_110.qpnetmonitor.data.remote.login.impl.LoginDataSourceDebug
import com.slava_110.qpnetmonitor.data.remote.sim.SimDataSource
import com.slava_110.qpnetmonitor.data.remote.sim.impl.SimDataSourceDebug
import com.slava_110.qpnetmonitor.data.repository.LoginRepository
import com.slava_110.qpnetmonitor.data.repository.SimRepository
import com.slava_110.qpnetmonitor.data.repository.impl.LoginRepositoryImpl
import com.slava_110.qpnetmonitor.data.repository.impl.SimRepositoryImpl
import com.slava_110.qpnetmonitor.ui.login.LoginViewModel
import com.slava_110.qpnetmonitor.ui.main.fragment.selector.SelectorViewModel
import com.slava_110.qpnetmonitor.ui.main.fragment.simulation.SimulationViewModel
import com.slava_110.qpnetmonitor.ui.main.fragment.menu.MenuViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                module {
                    single { UserCredentialsStore(get()) }

                    single<LoginRepository> { LoginRepositoryImpl(get(), get()) }
                    single<LoginDataSource> { LoginDataSourceDebug() }

                    single<SimRepository> { SimRepositoryImpl(get()) }
                    single<SimDataSource> { SimDataSourceDebug(get()) }

                    viewModelOf(::MenuViewModel)
                    viewModelOf(::SelectorViewModel)
                    viewModelOf(::LoginViewModel)
                    viewModel<SimulationViewModel>() { params -> SimulationViewModel(get()) }
                }
            )
        }

        Log.d("Koin", "Scope created")
    }
}