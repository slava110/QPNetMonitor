package com.slava_110.qpnetmonitor.ui.main.fragment.selector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.slava_110.qpnetmonitor.R
import com.slava_110.qpnetmonitor.databinding.FragmentSelectorBinding
import com.slava_110.qpnetmonitor.databinding.FragmentSelectorItemBinding
import com.slava_110.qpnetmonitor.ui.main.fragment.simulation.SimulationFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectorFragment: Fragment(R.layout.fragment_selector) {
    val viewModel by viewModel<SelectorViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentSelectorBinding.bind(view)

        binding.modelsListView.adapter = SelectorListAdapter().also { adapter ->
            lifecycleScope.launch {
                val list = viewModel.listModels()

                withContext(Dispatchers.Main) {
                    adapter.submitList(list)
                }
            }
        }
    }

    inner class SelectorListAdapter(): ListAdapter<String, SelectorViewHolder>(SelectorComparator) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectorViewHolder =
            SelectorViewHolder(
                FragmentSelectorItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        override fun onBindViewHolder(holder: SelectorViewHolder, position: Int) {
            val cur = getItem(position)
            holder.bind(cur)
        }

    }

    inner class SelectorViewHolder(private val binding: FragmentSelectorItemBinding) : ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                parentFragmentManager.commit {
                    replace<SimulationFragment>(R.id.activity_main)
                }
            }
        }

        fun bind(text: String) {
            binding.textView4.text = text
        }
    }

    object SelectorComparator: DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem.equals(newItem)

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem.contentEquals(newItem)

    }
}