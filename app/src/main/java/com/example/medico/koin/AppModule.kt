package com.example.medico.koin

import com.example.medico.models.AuthViewModel
import com.example.medico.models.DocAccountViewModel
import com.example.medico.models.UserAccountViewModel
import com.example.medico.sharedPreferences.SharedPreferencesManager
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClient(OkHttp) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
    }
    single<ApiService> { ApiServiceImpl(get()) }
    single { SharedPreferencesManager(get()) }
    viewModel { DocAccountViewModel(get()) }
    viewModel { UserAccountViewModel(get()) }
    viewModel { AuthViewModel(get()) }
}
