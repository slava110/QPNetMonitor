package com.slava_110.qpnetmonitor.ui.main.fragment.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.slava_110.qpnetmonitor.R
import com.slava_110.qpnetmonitor.databinding.FragmentMenuBinding
import com.slava_110.qpnetmonitor.ui.main.fragment.info.AboutAuthorFragment
import com.slava_110.qpnetmonitor.ui.main.fragment.info.AboutFragment
import com.slava_110.qpnetmonitor.ui.main.fragment.selector.SelectorFragment

class MenuFragment: Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentMenuBinding.bind(view)

        binding.butSelModel.setOnClickListener {
            parentFragmentManager.commit {
                replace<SelectorFragment>(R.id.activity_main)
            }
        }

        binding.butAbout.setOnClickListener {
            parentFragmentManager.commit {
                replace<AboutFragment>(R.id.activity_main)
            }
        }

        binding.butAuthor.setOnClickListener {
            parentFragmentManager.commit {
                replace<AboutAuthorFragment>(R.id.activity_main)
            }
        }

        binding.butLogout.setOnClickListener {
            requireActivity().finish()
        }
    }
}