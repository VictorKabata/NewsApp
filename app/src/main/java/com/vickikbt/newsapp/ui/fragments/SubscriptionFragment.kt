package com.vickikbt.newsapp.ui.fragments

import android.content.Intent
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
import com.vickikbt.newsapp.util.Constants
import com.vickikbt.newsapp.util.toast
import net.kosev.billing.Billing
import net.kosev.billing.Billing.*
import net.kosev.billing.Inventory
import net.kosev.billing.Purchase





class SubscriptionFragment : Fragment() {

    private val REQUEST_PURCHASE = 1001
    private lateinit var binding: FragmentSubscriptionBinding
    private var mBilling: Billing? = null

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


        mBilling = Billing(requireContext(), Constants.LICENSE_KEY)
        mBilling!!.create(object : CreateListener {
            override fun onSuccess() {
                val skus: ArrayList<String> = ArrayList()
                skus.add("intermediate")
                skus.add("expert")
                mBilling!!.loadInventory(skus, object : InventoryListener {
                    override fun onSuccess(inventory: Inventory) {
                        // inventory.hasPurchase("Your SKU");
                    }

                    override fun onError(response: Int, e: Exception) {
                        // show error
                        requireActivity().toast(e.localizedMessage)
                    }
                })
            }

            override fun onError(response: Int, e: Exception) {
                // billing is not supported
            }
        })


        binding.buttonSubscribeRate1.setOnClickListener {
            //billingBottomFragment.show(childFragmentManager, "intermediate")

            mBilling!!.launchPurchaseFlow(requireActivity(),
                "intermediate",
                TYPE_SUBS,
                REQUEST_PURCHASE,
                object : PurchaseListener {
                    override fun onSuccess(purchase: Purchase) {
                        if ("intermediate" == purchase.sku) {
                            // complete order
                        }
                    }

                    override fun onError(response: Int, e: Exception) {
                        // show error
                        requireActivity().toast(e.localizedMessage)
                    }
                })
        }

        binding.buttonSubscribeRate2.setOnClickListener {
            //billingBottomFragment.show(childFragmentManager, "expert")
            mBilling!!.launchPurchaseFlow(requireActivity(),
                "expert",
                TYPE_SUBS,
                REQUEST_PURCHASE,
                object : PurchaseListener {
                    override fun onSuccess(purchase: Purchase) {
                        if ("expert" == purchase.sku) {
                            // complete order
                        }
                    }

                    override fun onError(response: Int, e: Exception) {
                        // show error
                        requireActivity().toast(e.localizedMessage)
                    }
                })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (mBilling != null) {
            mBilling!!.onActivityResult(requestCode, resultCode, data);
        }
    }

}