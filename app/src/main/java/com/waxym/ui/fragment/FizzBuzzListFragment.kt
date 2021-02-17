package com.waxym.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialSharedAxis
import com.waxym.databinding.FragmentFizzbuzzListBinding
import com.waxym.ui.adapter.FizzBuzzItemAdapter
import com.waxym.ui.viewmodel.FizzBuzzListViewModel
import com.waxym.utils.extension.materialSharedAxis

class FizzBuzzListFragment : Fragment() {
    private lateinit var binding: FragmentFizzbuzzListBinding
    private val navArg: FizzBuzzListFragmentArgs by navArgs()
    private val viewModel: FizzBuzzListViewModel by viewModels { FizzBuzzListViewModel.Factory(navArg.fizzBuzzFormId) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFizzbuzzListBinding.inflate(inflater, container, false)
        enterTransition = materialSharedAxis(MaterialSharedAxis.Z, true)
        exitTransition = materialSharedAxis(MaterialSharedAxis.Z, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = FizzBuzzItemAdapter().also { adapter ->
            viewModel.data.observe(viewLifecycleOwner) {
                adapter.setData(it)
                adapter.notifyDataSetChanged()
            }
        }
    }
}