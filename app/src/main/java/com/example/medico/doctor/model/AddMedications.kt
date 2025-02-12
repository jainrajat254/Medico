package com.example.medico.doctor.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.medico.common.model.Frequency

class AddMedications : ViewModel() {
    var medication = mutableStateOf("")
    var dosage = mutableStateOf("")
    var time = mutableStateOf("")
    var frequency = mutableStateOf<Frequency?>(null)
    var frequencyExpanded = mutableStateOf(false)

}