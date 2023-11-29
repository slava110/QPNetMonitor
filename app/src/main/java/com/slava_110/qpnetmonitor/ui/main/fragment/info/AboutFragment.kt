package com.slava_110.qpnetmonitor.ui.main.fragment.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.slava_110.qpnetmonitor.R
import com.slava_110.qpnetmonitor.databinding.FragmentAboutBinding

class AboutFragment : Fragment(R.layout.fragment_about) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentAboutBinding.bind(view)

        binding.butUsage.setOnClickListener {
            parentFragmentManager.commit {
                replace<InstructionFragment>(R.id.activity_main)
            }
        }

        binding.butBack2.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}