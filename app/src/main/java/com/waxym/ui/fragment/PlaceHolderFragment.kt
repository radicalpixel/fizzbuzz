package com.waxym.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialSharedAxis
import com.waxym.databinding.FragmentPlaceholderBinding
import com.waxym.utils.extension.materialSharedAxis

class PlaceHolderFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentPlaceholderBinding.inflate(inflater, container, false)
        enterTransition = materialSharedAxis(MaterialSharedAxis.Z, true)
        exitTransition = materialSharedAxis(MaterialSharedAxis.Z, false)
        return binding.root
    }
}