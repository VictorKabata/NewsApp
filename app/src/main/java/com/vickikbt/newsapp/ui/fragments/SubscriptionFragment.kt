package com.vickikbt.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vickikbt.newsapp.R
import com.vickikbt.newsapp.databinding.FragmentSubscriptionBinding
import com.vickikbt.newsapp.ui.adapters.BillingBottomFragment


class SubscriptionFragment : Fragment() {

    private lateinit var binding: FragmentSubscriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_subscription, container, false)


        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        initUI()

        return binding.root
    }

    private fun initUI() {
        val billingBottomFragment = BillingBottomFragment()

        binding.buttonSubscribeRate1.setOnClickListener {
            billingBottomFragment.show(childFragmentManager, "VickiKbt")
        }

        binding.buttonSubscribeRate2.setOnClickListener {
            billingBottomFragment.show(childFragmentManager, "VickiKbt")
        }
    }

}