package com.example.medico

import android.app.Application
import com.example.medico.di.networkModule
import com.example.medico.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Medico : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Medico)
            modules(listOf(appModule, networkModule))
        }
    }
}