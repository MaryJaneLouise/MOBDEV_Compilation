package com.mariejuana.mobdevcompilation.ui.basics.oddeven

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OddEvenViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "There's no number checked as of today. Please enter a number first."
    }
    val text: LiveData<String> = _text
}