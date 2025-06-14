package com.example.medico.di

import com.example.medico.data.remote.ApiServiceImpl
import com.example.medico.data.repository.AppointmentsRepositoryImpl
import com.example.medico.data.repository.AuthRepositoryImpl
import com.example.medico.data.repository.MedicationsRepositoryImpl
import com.example.medico.data.repository.RecordsRepositoryImpl
import com.example.medico.data.repository.ReportsRepositoryImpl
import com.example.medico.data.repository.SettingsRepositoryImpl
import com.example.medico.domain.repository.AppointmentsRepository
import com.example.medico.domain.repository.AuthRepository
import com.example.medico.domain.repository.MedicationsRepository
import com.example.medico.domain.repository.RecordsRepository
import com.example.medico.domain.repository.ReportsRepository
import com.example.medico.domain.repository.SettingsRepository
import com.example.medico.domain.service.ApiService
import com.example.medico.presentation.viewmodel.AppointmentsViewModel
import com.example.medico.presentation.viewmodel.AuthViewModel
import com.example.medico.presentation.viewmodel.MedicationsViewModel
import com.example.medico.presentation.viewmodel.RecordsViewModel
import com.example.medico.presentation.viewmodel.ReportsViewModel
import com.example.medico.presentation.viewmodel.SettingsViewModel
import com.example.medico.utils.SharedPreferencesManager
import org.koin.core.module.dsl.*
import org.koin.dsl.module

val appModule = module {

    single<ApiService> { ApiServiceImpl(get()) }
    single { SharedPreferencesManager(get()) }

    single { get<SharedPreferencesManager>().getUserProfile()?.id.orEmpty() }

    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<AppointmentsRepository> { AppointmentsRepositoryImpl(get()) }
    single<MedicationsRepository> { MedicationsRepositoryImpl(get()) }
    single<RecordsRepository> { RecordsRepositoryImpl(get()) }
    single<ReportsRepository> { ReportsRepositoryImpl(get()) }
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }

    viewModel { AuthViewModel(get()) }

    viewModel { AppointmentsViewModel(get()) }
    viewModel { MedicationsViewModel(get()) }
    viewModel { RecordsViewModel(get()) }
    viewModel { ReportsViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}


