package com.slava_110.qpnetmonitor.ui.main.fragment.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.slava_110.qpnetmonitor.R
import com.slava_110.qpnetmonitor.databinding.FragmentAboutAuthorBinding

class AboutAuthorFragment : Fragment(R.layout.fragment_about_author) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentAboutAuthorBinding.bind(view)

        binding.butBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}