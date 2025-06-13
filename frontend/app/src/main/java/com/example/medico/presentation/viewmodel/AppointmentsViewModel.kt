package com.example.medico.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medico.domain.model.AppointmentDTO
import com.example.medico.domain.model.Appointments
import com.example.medico.domain.model.AppointmentsResponse
import com.example.medico.domain.repository.AppointmentsRepository
import com.example.medico.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppointmentsViewModel(
    private val appointmentsRepository: AppointmentsRepository,
) : ViewModel() {

    private val _addAppointmentState = MutableStateFlow<ResultState<Appointments>>(ResultState.Idle)
    val addAppointmentState: StateFlow<ResultState<Appointments>> = _addAppointmentState

    private val _getAppointmentState =
        MutableStateFlow<ResultState<List<AppointmentsResponse>>>(ResultState.Idle)
    val getAppointmentState: MutableStateFlow<ResultState<List<AppointmentsResponse>>> =
        _getAppointmentState

    private val _getAppointmentsForTodayState =
        MutableStateFlow<ResultState<List<AppointmentDTO>>>(ResultState.Idle)
    val getAppointmentsForTodayState: StateFlow<ResultState<List<AppointmentDTO>>> =
        _getAppointmentsForTodayState

    private val _markAppointmentAsDone = MutableStateFlow<ResultState<Boolean>>(ResultState.Idle)
    val markAppointmentAsDone: StateFlow<ResultState<Boolean>> = _markAppointmentAsDone

    private val _markAppointmentAsAbsent = MutableStateFlow<ResultState<Boolean>>(ResultState.Idle)
    val markAppointmentAsAbsent: StateFlow<ResultState<Boolean>> = _markAppointmentAsAbsent

    private val _absentAppointmentsState =
        MutableStateFlow<ResultState<List<AppointmentDTO>>>(ResultState.Idle)
    val absentAppointmentsState: StateFlow<ResultState<List<AppointmentDTO>>> =
        _absentAppointmentsState

    private val _pastAppointmentsState =
        MutableStateFlow<ResultState<List<AppointmentDTO>>>(ResultState.Idle)
    val pastAppointmentsState: StateFlow<ResultState<List<AppointmentDTO>>> = _pastAppointmentsState

    private val _futureAppointmentsState =
        MutableStateFlow<ResultState<List<AppointmentDTO>>>(ResultState.Idle)
    val futureAppointmentsState: StateFlow<ResultState<List<AppointmentDTO>>> =
        _futureAppointmentsState

    private val _doctorAppointmentsState =
        MutableStateFlow<ResultState<List<AppointmentDTO>>>(ResultState.Idle)
    val doctorAppointmentsState: StateFlow<ResultState<List<AppointmentDTO>>> =
        _doctorAppointmentsState

    fun loadAppointmentsForCurrentUser(userId: String) {
        if (_getAppointmentState.value is ResultState.Success) return
        if (userId.isNotBlank()) {
            getAppointments(userId)
            Log.d(
                "AppointmentsViewModel",
                "loadAppointmentsForCurrentUser: fetching appointments for user $userId"
            )
        } else {
            Log.d(
                "AppointmentsViewModel",
                "loadAppointmentsForCurrentUser: userId is blank, skipping fetch"
            )
        }
    }

    private fun <T> launchWithResult(
        state: MutableStateFlow<ResultState<T>>,
        block: suspend () -> T,
    ) {
        viewModelScope.launch {
            state.value = ResultState.Loading
            try {
                val result = withContext(Dispatchers.IO) {
                    block()
                }
                state.value = ResultState.Success(result)
            } catch (e: Exception) {
                state.value = ResultState.Error(e)
            }
        }
    }

    fun addAppointments(request: Appointments, userId: String) {
        launchWithResult(_addAppointmentState) {
            appointmentsRepository.addAppointments(request, userId)
        }
    }

    fun getAppointments(userId: String) {
        launchWithResult(_getAppointmentState) {
            appointmentsRepository.getAppointments(userId)
        }
    }

    fun getAppointmentsForToday(doctorId: String) {
        launchWithResult(_getAppointmentsForTodayState) {
            appointmentsRepository.getAppointmentsForToday(doctorId)
        }
    }

    fun markAppointmentAsDone(appointmentId: String) {
        launchWithResult(_markAppointmentAsDone) {
            appointmentsRepository.markAppointmentAsDone(appointmentId)
        }
    }

    fun markAppointmentAsAbsent(appointmentId: String) {
        launchWithResult(_markAppointmentAsAbsent) {
            appointmentsRepository.markAppointmentAsAbsent(appointmentId)
        }
    }

    fun getAbsentAppointmentsForToday(doctorId: String) {
        launchWithResult(_absentAppointmentsState) {
            appointmentsRepository.getAbsentAppointmentsForToday(doctorId)
        }
    }

    fun getPastAppointments(doctorId: String) {
        launchWithResult(_pastAppointmentsState) {
            appointmentsRepository.getPastAppointments(doctorId)
        }
    }

    fun getFutureAppointments(doctorId: String) {
        launchWithResult(_futureAppointmentsState) {
            appointmentsRepository.getFutureAppointments(doctorId)
        }
    }

    fun getDoctorAppointments(doctorId: String) {
        launchWithResult(_doctorAppointmentsState) {
            appointmentsRepository.getDoctorAppointments(doctorId)
        }
    }
}
