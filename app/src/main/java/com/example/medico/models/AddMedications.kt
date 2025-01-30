package com.example.medico.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.medico.data.Frequency

class AddMedications : ViewModel() {
    var medication = mutableStateOf("")
    var dosage = mutableStateOf("")
    var time = mutableStateOf("")
    var frequency = mutableStateOf<Frequency?>(null)
    var frequencyExpanded = mutableStateOf(false)

}