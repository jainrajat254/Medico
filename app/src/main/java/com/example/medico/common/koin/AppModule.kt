package com.example.medico.common.koin

import com.example.medico.common.viewModel.AuthViewModel
import com.example.medico.doctor.viewModel.DoctorDetails
import com.example.medico.user.viewModel.UserDetails
import com.example.medico.common.sharedPreferences.SharedPreferencesManager
import com.example.medico.doctor.viewModel.AddMedications
import com.example.medico.doctor.viewModel.DoctorRegister
import com.example.medico.user.viewModel.AppointmentsViewModel
import com.example.medico.user.viewModel.ReportsViewModel
import com.example.medico.user.viewModel.UserOverviewViewModel
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
                    coerceInputValues = true
                })
            }
        }
    }
    single<ApiService> { ApiServiceImpl(get()) }
    single { SharedPreferencesManager(get()) }
    viewModel { DoctorDetails(get()) }
    viewModel { UserDetails(get()) }
    viewModel { DoctorRegister(get()) }  // Ensure this is correctly added
    viewModel { AuthViewModel(get(),get()) }
    viewModel { UserOverviewViewModel(get()) }
    viewModel { AppointmentsViewModel(get()) }
    viewModel { AddMedications(get(),get()) }
    viewModel { ReportsViewModel(get(),get()) }
}
