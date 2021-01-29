package com.vickikbt.newsapp.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vickikbt.newsapp.R
import com.vickikbt.newsapp.databinding.FragmentIntroBinding
import com.vickikbt.newsapp.util.log


class IntroFragment : Fragment() {

    private lateinit var binding: FragmentIntroBinding
    private lateinit var window:Window

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_intro, container, false)
        window=requireActivity().window

        binding.buttonBrowse.setOnClickListener {
            findNavController().navigate(R.id.intro_to_home)
        }

        binding.buttonUpgrade.setOnClickListener {
            findNavController().navigate(R.id.intro_to_subscription)
        }

        initUI()

        return binding.root
    }

    private fun initUI() {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = Color.TRANSPARENT
    }

    override fun onPause() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        super.onPause()
    }
}