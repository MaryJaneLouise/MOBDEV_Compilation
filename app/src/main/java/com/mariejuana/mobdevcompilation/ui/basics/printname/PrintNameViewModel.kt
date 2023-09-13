package com.mariejuana.mobdevcompilation.ui.basics.printname

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PrintNameViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is a test only for print name."
    }
    val text: LiveData<String> = _text
}