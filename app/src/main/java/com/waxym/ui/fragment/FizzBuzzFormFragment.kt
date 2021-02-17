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
import com.waxym.utils.extension.materialSharedAxis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FizzBuzzFormFragment : Fragment() {
    private lateinit var binding: FragmentFizzbuzzFormBinding
    private val viewModel: FormViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFizzbuzzFormBinding.inflate(inflater, container, false).also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        enterTransition = materialSharedAxis(MaterialSharedAxis.Z, true)
        exitTransition = materialSharedAxis(MaterialSharedAxis.Z, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.actionCompute.setOnClickListener {
            CoroutineScope(Job() + Dispatchers.Main).launch {
                viewModel.doOnValidForm(binding) {
                    navigateToFizzBuzzList(it.uid)
                }
            }
        }
    }

    private fun navigateToFizzBuzzList(fizzBuzzFormId: Long) {
        val direction = FizzBuzzFormFragmentDirections.navigateToList(fizzBuzzFormId)
        findNavController().navigate(direction)
    }
}