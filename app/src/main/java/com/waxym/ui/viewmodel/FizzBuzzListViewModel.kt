package com.waxym.ui.viewmodel

import androidx.lifecycle.*
import com.waxym.data.database.FizzBuzzDatabase
import com.waxym.utils.buildFizzBuzzList
import com.waxym.utils.injection.inject
import kotlinx.coroutines.launch


class FizzBuzzListViewModel(formId: Long) : ViewModel() {
    private val database: FizzBuzzDatabase by inject()

    private val _data = MutableLiveData<List<String>>()
    val data: LiveData<List<String>> get() = _data

    init {
        viewModelScope.launch {
            val item = database.formDao().get(formId)
            val uio: List<String> = buildFizzBuzzList(
                start = 1,
                end = item.limit,
                fizzMultiple = item.fizzMultiple,
                fizzLabel = item.fizzLabel,
                buzzMultiple = item.buzzMultiple,
                buzzLabel = item.buzzLabel
            )
            _data.postValue(uio)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val formId: Long) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FizzBuzzListViewModel(formId) as T
        }
    }
}