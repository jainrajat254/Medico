package com.example.medico.doctor.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AddMedications : ViewModel() {

    private val _medication = MutableStateFlow("")
    val medication: StateFlow<String> = _medication

    private val _medicationType = MutableStateFlow("")
    val medicationType: StateFlow<String> = _medicationType

    private val _dosageType = MutableStateFlow("")
    val dosageType: StateFlow<String> = _dosageType

    private val _time = MutableStateFlow("")
    val time: StateFlow<String> = _time

    private val _frequency = MutableStateFlow("")
    val frequency: StateFlow<String> = _frequency

    private val _duration = MutableStateFlow("")
    val duration: StateFlow<String> = _duration

    private val _intakeMethod = MutableStateFlow("")
    val intakeMethod: StateFlow<String> = _intakeMethod

    // Functions to update state
    fun setMedication(value: String) { _medication.value = value }
    fun setMedicationType(value: String) { _medicationType.value = value }
    fun setDosageType(value: String) { _dosageType.value = value }
    fun setTime(value: String) { _time.value = value }
    fun setFrequency(value: String) { _frequency.value = value }
    fun setDuration(value: String) { _duration.value = value }
    fun setIntakeMethod(value: String) { _intakeMethod.value = value }
}
