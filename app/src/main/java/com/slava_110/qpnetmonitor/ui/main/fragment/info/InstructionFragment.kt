package com.slava_110.qpnetmonitor.ui.main.fragment.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.slava_110.qpnetmonitor.R
import com.slava_110.qpnetmonitor.databinding.FragmentInstructionBinding

class InstructionFragment : Fragment(R.layout.fragment_instruction) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentInstructionBinding.bind(view)

        binding.butBack2.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}