package com.slava_110.qpnetmonitor.ui.main.fragment.simulation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.slava_110.qpnetmonitor.R

class SimulationFragment : Fragment(R.layout.fragment_simulation) {
    val viewModel by viewModels<SimulationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}