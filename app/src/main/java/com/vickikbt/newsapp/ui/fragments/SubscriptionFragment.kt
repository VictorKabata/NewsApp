package com.vickikbt.newsapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.BillingProcessor.IBillingHandler
import com.anjlab.android.iab.v3.TransactionDetails
import com.vickikbt.newsapp.R
import com.vickikbt.newsapp.databinding.FragmentSubscriptionBinding
import com.vickikbt.newsapp.ui.adapters.BillingBottomFragment
import com.vickikbt.newsapp.util.Constants.LICENSE_KEY
import com.vickikbt.newsapp.util.UserSession
import com.vickikbt.newsapp.util.log
import com.vickikbt.newsapp.util.toast


class SubscriptionFragment : Fragment() {

    private lateinit var binding: FragmentSubscriptionBinding

    private lateinit var billingProcessor: BillingProcessor
    private lateinit var userSession: UserSession

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_subscription,
            container,
            false
        )

        userSession = UserSession(requireActivity())


        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        initBilling()
        initUI()

        return binding.root
    }

    private fun initUI() {
        val billingBottomFragment = BillingBottomFragment()

        binding.buttonSubscribeRate1.setOnClickListener {
            billingProcessor.purchase(requireActivity(), "productId")
        }

        binding.buttonSubscribeRate2.setOnClickListener {
            billingProcessor.purchase(requireActivity(), "productId")
        }
    }

    private fun initBilling() {
        billingProcessor =
            BillingProcessor(requireActivity(), LICENSE_KEY, null, object : IBillingHandler {
                override fun onProductPurchased(productId: String, details: TransactionDetails?) {
                    userSession.setUserSubscribed(true)
                }

                override fun onPurchaseHistoryRestored() {}

                override fun onBillingError(errorCode: Int, error: Throwable?) {
                    userSession.setUserSubscribed(false)
                    requireActivity().toast("${error?.message}")
                }

                override fun onBillingInitialized() {
                    requireActivity().log("Billing Initialized")
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        billingProcessor.handleActivityResult(requestCode, resultCode, data)
    }

}