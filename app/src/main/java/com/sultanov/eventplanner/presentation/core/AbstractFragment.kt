package com.sultanov.eventplanner.presentation.core

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class AbstractFragment<T : ViewBinding>(
    @LayoutRes idLayout: Int
) : Fragment(idLayout) {

    private var _binding: T? = null
    protected val binding: T get() = _binding!!

    protected abstract fun bind(view: View): T

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}