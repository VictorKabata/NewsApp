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
import com.anjlab.android.iab.v3.TransactionDetails
import com.vickikbt.newsapp.R
import com.vickikbt.newsapp.databinding.FragmentSubscriptionBinding
import com.vickikbt.newsapp.ui.adapters.BillingBottomFragment
import com.vickikbt.newsapp.util.Constants
import com.vickikbt.newsapp.util.toast


class SubscriptionFragment : Fragment(), BillingProcessor.IBillingHandler {

    private lateinit var binding: FragmentSubscriptionBinding

    var bp: BillingProcessor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_subscription, container, false)


        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        bp = BillingProcessor(requireContext(), Constants.LICENSE_KEY, this)
        bp?.initialize()

        initUI()

        return binding.root
    }

    private fun initUI() {
        val billingBottomFragment = BillingBottomFragment()

        binding.buttonSubscribeRate1.setOnClickListener {
            //billingBottomFragment.show(childFragmentManager, "intermediate")
            bp?.subscribe(requireActivity(), "intermediate")
        }

        binding.buttonSubscribeRate2.setOnClickListener {
            //billingBottomFragment.show(childFragmentManager, "expert")
            bp?.subscribe(requireActivity(), "expert")
        }
    }

    override fun onBillingInitialized() {

    }

    override fun onProductPurchased(productId: String, details: TransactionDetails?) {

    }

    override fun onPurchaseHistoryRestored() {

    }

    override fun onBillingError(errorCode: Int, error: Throwable?) {
        requireActivity().toast("${error?.localizedMessage}")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun onDestroy() {
        bp?.release()
        super.onDestroy()
    }
}