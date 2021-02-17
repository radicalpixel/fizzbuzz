package com.waxym.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FormViewModel : ViewModel() {
    val fizzMultiple = MutableLiveData<String>()
    val fizzLabel = MutableLiveData<String>()
    val buzzMultiple = MutableLiveData<String>()
    val buzzLabel = MutableLiveData<String>()
    val limit = MutableLiveData<String>()
}