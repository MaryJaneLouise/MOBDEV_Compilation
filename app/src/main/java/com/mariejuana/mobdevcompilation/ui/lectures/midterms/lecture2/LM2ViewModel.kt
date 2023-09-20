package com.mariejuana.mobdevcompilation.ui.lectures.midterms.lecture2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LM2ViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "There's no number checked as of today. Please enter a number first."
    }
    val text: LiveData<String> = _text
}