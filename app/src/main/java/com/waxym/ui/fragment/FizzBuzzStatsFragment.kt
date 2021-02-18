package com.waxym.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.transition.MaterialSharedAxis
import com.waxym.R
import com.waxym.databinding.FragmentFizzbuzzStatsBinding
import com.waxym.ui.adapter.FizzBuzzStatsAdapter
import com.waxym.ui.viewmodel.FizzBuzzStatsViewModel
import com.waxym.utils.decorator.SpaceDecorator
import com.waxym.utils.extension.materialSharedAxis

class FizzBuzzStatsFragment : Fragment() {
    private lateinit var binding: FragmentFizzbuzzStatsBinding
    private val viewModel: FizzBuzzStatsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFizzbuzzStatsBinding.inflate(inflater, container, false)
        enterTransition = materialSharedAxis()
        exitTransition = materialSharedAxis()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.addItemDecoration(SpaceDecorator.Last(bottom = R.dimen.margin_default))
        binding.recyclerView.adapter = FizzBuzzStatsAdapter().also { adapter ->
            viewModel.stats.observe(viewLifecycleOwner) {
                adapter.setData(it)
                adapter.notifyDataSetChanged()
            }
        }
    }
}