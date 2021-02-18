package com.waxym.ui.viewmodel

import androidx.lifecycle.*
import androidx.paging.*
import com.waxym.data.database.FizzBuzzDatabase
import com.waxym.data.model.FizzBuzzForm
import com.waxym.utils.buildFizzBuzzList
import com.waxym.utils.injection.inject
import kotlinx.coroutines.launch


class FizzBuzzListViewModel(fizzMultiple: Int, fizzLabel: String, buzzMultiple: Int, buzzLabel: String, limit: Int) : ViewModel() {
    private val database: FizzBuzzDatabase by inject()

    private val _data = MediatorLiveData<PagedList<String>>()
    val data: LiveData<PagedList<String>> get() = _data

    init {
        viewModelScope.launch {
            val form = database.formDao().get(fizzMultiple, fizzLabel, buzzMultiple, buzzLabel, limit)
            if (form != null) {
                _data.addSource(LivePagedListBuilder(FizzBuzzDataSource.Factory(form), Config(pageSize = PAGE_SIZE, enablePlaceholders = false)).build()) {
                    _data.value = it
                }
            }
        }
    }

    class FizzBuzzDataSource(private val form: FizzBuzzForm) : PositionalDataSource<String>() {
        override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<String>) {
            val data = buildFizzBuzzList(
                start = params.requestedStartPosition,
                end = (params.requestedStartPosition + params.pageSize - 1).let { if (it < form.limit) it else form.limit },
                fizzMultiple = form.fizzMultiple,
                fizzLabel = form.fizzLabel,
                buzzMultiple = form.buzzMultiple,
                buzzLabel = form.buzzLabel
            )
            if (params.placeholdersEnabled) {
                callback.onResult(data.toMutableList(), params.requestedStartPosition, form.limit)
            } else {
                callback.onResult(data.toMutableList(), params.requestedStartPosition)
            }
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<String>) {
            val data = buildFizzBuzzList(
                start = params.startPosition,
                end = (params.startPosition + params.loadSize).let { if (it < form.limit) it else form.limit },
                fizzMultiple = form.fizzMultiple,
                fizzLabel = form.fizzLabel,
                buzzMultiple = form.buzzMultiple,
                buzzLabel = form.buzzLabel
            )
            callback.onResult(data.toMutableList())
        }

        class Factory(private val form: FizzBuzzForm) : DataSource.Factory<Int, String>() {
            override fun create(): DataSource<Int, String> = FizzBuzzDataSource(form)
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

    companion object {
        private const val PAGE_SIZE = 50
    }
}