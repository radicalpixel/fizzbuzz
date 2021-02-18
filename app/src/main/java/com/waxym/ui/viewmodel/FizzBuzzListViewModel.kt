package com.waxym.ui.viewmodel

import androidx.lifecycle.*
import com.waxym.data.database.FizzBuzzDatabase
import com.waxym.utils.buildFizzBuzzList
import com.waxym.utils.injection.inject
import kotlinx.coroutines.launch


class FizzBuzzListViewModel(fizzMultiple: Int, fizzLabel: String, buzzMultiple: Int, buzzLabel: String, limit: Int) : ViewModel() {
    private val database: FizzBuzzDatabase by inject()

    private val _data = MutableLiveData<List<String>>()
    val data: LiveData<List<String>> get() = _data

    init {
        viewModelScope.launch {
            val form = database.formDao().get(fizzMultiple, fizzLabel, buzzMultiple, buzzLabel, limit)
            if (form != null) {
                val uio: List<String> = buildFizzBuzzList(
                    start = 1,
                    end = form.limit,
                    fizzMultiple = form.fizzMultiple,
                    fizzLabel = form.fizzLabel,
                    buzzMultiple = form.buzzMultiple,
                    buzzLabel = form.buzzLabel
                )
                _data.postValue(uio)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val fizzMultiple: Int,
        private val fizzLabel: String,
        private val buzzMultiple: Int,
        private val buzzLabel: String,
        private val limit: Int
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FizzBuzzListViewModel(fizzMultiple, fizzLabel, buzzMultiple, buzzLabel, limit) as T
        }
    }
}