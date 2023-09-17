package com.mariejuana.mobdevcompilation.ui.basics.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is a test only for calculator."
    }
    val text: LiveData<String> = _text
}