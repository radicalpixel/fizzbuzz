package com.waxym.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.google.android.material.transition.MaterialSharedAxis
import com.waxym.R
import com.waxym.databinding.FragmentFizzbuzzListBinding
import com.waxym.ui.adapter.FizzBuzzItemAdapter
import com.waxym.ui.viewmodel.FizzBuzzListViewModel
import com.waxym.utils.extension.materialSharedAxis

class FizzBuzzListFragment : Fragment() {
    private lateinit var binding: FragmentFizzbuzzListBinding
    private val navArg: FizzBuzzListFragmentArgs by navArgs()
    private val viewModel: FizzBuzzListViewModel by viewModels { FizzBuzzListViewModel.Factory(navArg.fizzMultiple, navArg.fizzLabel, navArg.buzzMultiple, navArg.buzzLabel, navArg.limit) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        binding = FragmentFizzbuzzListBinding.inflate(inflater, container, false)
        enterTransition = materialSharedAxis()
        exitTransition = materialSharedAxis()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = FizzBuzzItemAdapter().also { adapter ->
            viewModel.data.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_stats, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, findNavController()) || super.onOptionsItemSelected(item)
    }
}