package com.waxym.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.waxym.R
import com.waxym.databinding.FragmentFizzbuzzFormBinding
import com.waxym.ui.viewmodel.FizzBuzzFormViewModel
import com.waxym.utils.extension.materialSharedAxis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FizzBuzzFormFragment : Fragment() {
    private lateinit var binding: FragmentFizzbuzzFormBinding
    private val viewModel: FizzBuzzFormViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        binding = FragmentFizzbuzzFormBinding.inflate(inflater, container, false).also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        enterTransition = materialSharedAxis()
        exitTransition = materialSharedAxis()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.actionCompute.setOnClickListener {
            CoroutineScope(Job() + Dispatchers.Main).launch {
                viewModel.doOnValidForm(binding) {
                    navigateToFizzBuzzList(it.fizzMultiple, it.fizzLabel, it.buzzMultiple, it.buzzLabel, it.limit)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_stats, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        exitTransition = materialSharedAxis()
        reenterTransition = materialSharedAxis()
        return NavigationUI.onNavDestinationSelected(item, findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun navigateToFizzBuzzList(fizzMultiple: Int, fizzLabel: String, buzzMultiple: Int, buzzLabel: String, limit: Int) {
        val direction = FizzBuzzFormFragmentDirections.navigateToList(fizzMultiple, fizzLabel, buzzMultiple, buzzLabel, limit)
        exitTransition = materialSharedAxis()
        reenterTransition = materialSharedAxis()
        findNavController().navigate(direction)
    }
}