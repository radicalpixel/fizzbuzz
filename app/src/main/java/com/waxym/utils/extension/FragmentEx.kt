package com.waxym.utils.extension

import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialSharedAxis

fun Fragment.materialSharedAxis(@MaterialSharedAxis.Axis axis: Int, forward: Boolean) = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
    duration = resources.getInteger(android.R.integer.config_longAnimTime).toLong()
}
