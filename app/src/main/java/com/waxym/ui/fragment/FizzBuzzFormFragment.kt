package com.waxym.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialSharedAxis
import com.waxym.databinding.FragmentFizzbuzzFormBinding
import com.waxym.ui.viewmodel.FormViewModel
import com.waxym.utils.asInt
import com.waxym.utils.asString
import com.waxym.utils.requireNotBlank
import com.waxym.utils.requirePositiveInteger

class FizzBuzzFormFragment : Fragment() {
    private lateinit var binding: FragmentFizzbuzzFormBinding
    private val viewModel: FormViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFizzbuzzFormBinding.inflate(inflater, container, false).also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            duration = resources.getInteger(android.R.integer.config_longAnimTime).toLong()
        }
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
            duration = resources.getInteger(android.R.integer.config_longAnimTime).toLong()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.actionCompute.setOnClickListener {
            validateForm {
                navigateToFizzBuzzList()
            }
        }
    }

    private fun validateForm(lambda: () -> Unit) {
        val fizzMultiple: Int? = binding.inputLayoutFizzMultiple.requireNotBlank()?.requirePositiveInteger()?.asInt()
        val fizzLabel: String? = binding.inputLayoutFizzLabel.requireNotBlank()?.asString()
        val buzzMultiple: Int? = binding.inputLayoutBuzzMultiple.requireNotBlank()?.requirePositiveInteger()?.asInt()
        val buzzLabel: String? = binding.inputLayoutBuzzLabel.requireNotBlank()?.asString()
        val limit: Int? = binding.inputLayoutLimit.requireNotBlank()?.requirePositiveInteger()?.asInt()
        if (fizzMultiple != null && fizzLabel != null && buzzMultiple != null && buzzLabel != null && limit != null) {
            lambda()
        }
    }

    private fun navigateToFizzBuzzList() {
        val direction = FizzBuzzFormFragmentDirections.navigateToList()
        findNavController().navigate(direction)
    }
}