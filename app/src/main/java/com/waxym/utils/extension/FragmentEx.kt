package com.waxym.utils.extension

import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialSharedAxis

fun Fragment.materialSharedAxis(@MaterialSharedAxis.Axis axis: Int = MaterialSharedAxis.Z, forward: Boolean = true) =
    MaterialSharedAxis(axis, forward).apply {
        duration = resources.getInteger(android.R.integer.config_longAnimTime).toLong()
    }
