package com.waxym.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.waxym.data.dao.FizzBuzzFormDao
import com.waxym.data.database.FizzBuzzDatabase
import com.waxym.data.model.FizzBuzzForm
import com.waxym.databinding.FragmentFizzbuzzFormBinding
import com.waxym.utils.extension.asInt
import com.waxym.utils.extension.asString
import com.waxym.utils.extension.requireNotBlank
import com.waxym.utils.extension.requirePositiveInteger
import com.waxym.utils.injection.inject

class FizzBuzzFormViewModel : ViewModel() {
    private val database: FizzBuzzDatabase by inject()
    private val formDao: FizzBuzzFormDao get() = database.formDao()

    val fizzMultiple = MutableLiveData<String>("3")
    val fizzLabel = MutableLiveData<String>("fizz")
    val buzzMultiple = MutableLiveData<String>("5")
    val buzzLabel = MutableLiveData<String>("buzz")
    val limit = MutableLiveData<String>("100")

    suspend fun doOnValidForm(binding: FragmentFizzbuzzFormBinding, lambda: (fizzBuzzForm: FizzBuzzForm) -> Unit) {
        val fizzMultiple: Int? = binding.inputLayoutFizzMultiple.requireNotBlank()?.requirePositiveInteger()?.asInt()
        val fizzLabel: String? = binding.inputLayoutFizzLabel.requireNotBlank()?.asString()
        val buzzMultiple: Int? = binding.inputLayoutBuzzMultiple.requireNotBlank()?.requirePositiveInteger()?.asInt()
        val buzzLabel: String? = binding.inputLayoutBuzzLabel.requireNotBlank()?.asString()
        val limit: Int? = binding.inputLayoutLimit.requireNotBlank()?.requirePositiveInteger()?.asInt()

        if (fizzMultiple != null && fizzLabel != null && buzzMultiple != null && buzzLabel != null && limit != null) {
            val form: FizzBuzzForm = getForm(fizzMultiple, fizzLabel, buzzMultiple, buzzLabel, limit).also { form ->
                form.count += 1
                formDao.insertOrUpdate(form)
            }
            lambda(form)
        }
    }

    private suspend fun getForm(fizzMultiple: Int, fizzLabel: String, buzzMultiple: Int, buzzLabel: String, limit: Int): FizzBuzzForm {
        val form: FizzBuzzForm? = formDao.get(fizzMultiple, fizzLabel, buzzMultiple, buzzLabel, limit)
        return form ?: FizzBuzzForm(fizzMultiple, fizzLabel, buzzMultiple, buzzLabel, limit, 0)
    }
}