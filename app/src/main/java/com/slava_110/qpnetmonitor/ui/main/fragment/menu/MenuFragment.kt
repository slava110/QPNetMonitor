package com.slava_110.qpnetmonitor.ui.main.fragment.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import com.slava_110.qpnetmonitor.R
import com.slava_110.qpnetmonitor.databinding.FragmentMenuBinding
import com.slava_110.qpnetmonitor.ui.main.fragment.info.AboutAuthorFragment
import com.slava_110.qpnetmonitor.ui.main.fragment.info.AboutFragment
import com.slava_110.qpnetmonitor.ui.main.fragment.selector.SelectorFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuFragment: Fragment(R.layout.fragment_menu) {
    private val viewModel by viewModel<MenuViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentMenuBinding.bind(view)

        binding.butSelModel.setOnClickListener {
            parentFragmentManager.commit {
                replace<SelectorFragment>(R.id.activity_main)
                addToBackStack(null)
            }
        }

        binding.butAbout.setOnClickListener {
            parentFragmentManager.commit {
                replace<AboutFragment>(R.id.activity_main)
                addToBackStack(null)
            }
        }

        binding.butAuthor.setOnClickListener {
            parentFragmentManager.commit {
                replace<AboutAuthorFragment>(R.id.activity_main)
                addToBackStack(null)
            }
        }

        binding.butLogout.setOnClickListener {
            lifecycleScope.launch {
                viewModel.logout()
                requireActivity().finish()
            }
        }
    }
}