package com.slava_110.qpnetmonitor.ui.main.fragment.simulation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.slava_110.qpnetmonitor.R
import com.slava_110.qpnetmonitor.databinding.FragmentSelectorItemBinding
import com.slava_110.qpnetmonitor.databinding.FragmentSimulationBinding
import com.slava_110.qpnetmonitor.databinding.FragmentSimulationItemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.ParametersHolder

class SimulationFragment : Fragment(R.layout.fragment_simulation) {
    val name: String
        get() = arguments?.getString("simulation")!!
    private val viewModel by viewModel<SimulationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSimulationBinding.bind(view)

        binding.simName.text = name

        binding.butBack8.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.button2.setOnClickListener {
            viewModel.start()
        }

        binding.button.setOnClickListener {
            viewModel.stop()
        }

        binding.recyclerView3.adapter = SimulationListAdapter().also { adapter ->
            lifecycleScope.launch {
                Log.d("Simulation", "Waiting for changes")
                viewModel.steps
                    .map {
                        it.params.toList()
                    }
                    .collect { result ->
                        withContext(Dispatchers.Main) {
                            adapter.submitList(result)
                        }
                    }
            }
            viewModel.connect(name)
        }
    }


    inner class SimulationListAdapter: ListAdapter<Pair<String, ULong>, SimulationViewHolder>(SimulationComparator) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimulationViewHolder =
            SimulationViewHolder(
                FragmentSimulationItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        override fun onBindViewHolder(holder: SimulationViewHolder, position: Int) {
            val cur = getItem(position)
            holder.bind(cur)
        }

    }

    inner class SimulationViewHolder(private val binding: FragmentSimulationItemBinding) : ViewHolder(binding.root) {

        fun bind(data: Pair<String, ULong>) {
            binding.simParamName.text = data.first
            binding.simParamValue.text = data.second.toString()
        }
    }

    object SimulationComparator: DiffUtil.ItemCallback<Pair<String, ULong>>() {

        override fun areItemsTheSame(oldItem: Pair<String, ULong>, newItem: Pair<String, ULong>): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Pair<String, ULong>, newItem: Pair<String, ULong>): Boolean =
            oldItem == newItem

    }
}