package com.vickikbt.newsapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.billingclient.api.*
import com.google.android.material.snackbar.Snackbar
import com.vickikbt.newsapp.R
import com.vickikbt.newsapp.databinding.FragmentSubscriptionBinding
import com.vickikbt.newsapp.ui.adapters.BillingBottomFragment
import com.vickikbt.newsapp.util.Constants
import com.vickikbt.newsapp.util.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.kosev.billing.Billing
import net.kosev.billing.Billing.*
import net.kosev.billing.Inventory
import net.kosev.billing.Purchase


class SubscriptionFragment : Fragment(), PurchasesUpdatedListener,
    AcknowledgePurchaseResponseListener {

    private val REQUEST_PURCHASE = 1001
    private lateinit var binding: FragmentSubscriptionBinding
    private var mBilling: Billing? = null
    private var mSkuDetailsList: MutableList<SkuDetails?> = mutableListOf()

    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            // To be implemented in a later section.
        }


    private var billingClient = BillingClient.newBuilder(requireContext())
        .setListener(purchasesUpdatedListener)
        .enablePendingPurchases()
        .build()

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

        try {
            lifecycleScope.launch {
                initNewBiller()
            }
        } catch (e: Exception) {
            requireActivity().toast(e.localizedMessage)
            Log.e("TAG", "onCreateView: $e")
        }

        return binding.root
    }

    private suspend fun initNewBiller() {

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    requireActivity().toast("billing is ready")
                }
            }

            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                requireActivity().toast("billing is not ready!")
            }
        })

        querySkuDetails()

    }

    suspend fun querySkuDetails() {
        val skuList = ArrayList<String>()
        skuList.add("intermediate")
        skuList.add("expert")
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS)
        withContext(Dispatchers.IO) {
            billingClient.querySkuDetailsAsync(params.build()) { billingResult, skuDetailsList ->
                // Process the result.
                requireActivity().toast("products loaded!")

                mSkuDetailsList = skuDetailsList
            }
        }
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
                        Snackbar.make(
                            requireView(),
                            "purchases are now ready!",
                            Snackbar.LENGTH_SHORT
                        ).show()
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

            /*        mBilling!!.launchPurchaseFlow(requireActivity(),
                        "intermediate",
                        TYPE_SUBS,
                        REQUEST_PURCHASE,
                        object : PurchaseListener {
                            override fun onSuccess(purchase: Purchase) {
                                if ("intermediate" == purchase.sku) {
                                    // complete order
                                    requireActivity().toast("intermediate purchased successfully!")
                                }
                            }

                            override fun onError(response: Int, e: Exception) {
                                // show error
                                requireActivity().toast(e.localizedMessage)
                            }
                        })*/

            try {
                lifecycleScope.launch {

                    // Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().
                    val flowParams = BillingFlowParams.newBuilder()
                        .setSkuDetails(mSkuDetailsList.firstOrNull())
                        .build()
                    val responseCode =
                        billingClient.launchBillingFlow(activity, flowParams).responseCode
                }
            } catch (e: Exception) {
                requireActivity().toast(e.localizedMessage)
                Log.e("TAG", "initUI: $e")
            }

        }

        binding.buttonSubscribeRate2.setOnClickListener {
            //billingBottomFragment.show(childFragmentManager, "expert")
            /*           mBilling!!.launchPurchaseFlow(requireActivity(),
                           "expert",
                           TYPE_SUBS,
                           REQUEST_PURCHASE,
                           object : PurchaseListener {
                               override fun onSuccess(purchase: Purchase) {
                                   if ("expert" == purchase.sku) {
                                       // complete order
                                       requireActivity().toast("expert purchased successfully!")
                                   }
                               }

                               override fun onError(response: Int, e: Exception) {
                                   // show error
                                   requireActivity().toast(e.localizedMessage)
                               }
                           })*/

            try {
                lifecycleScope.launch {

                    // Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().
                    val flowParams = BillingFlowParams.newBuilder()
                        .setSkuDetails(mSkuDetailsList.lastOrNull())
                        .build()
                    val responseCode =
                        billingClient.launchBillingFlow(activity, flowParams).responseCode
                }
            } catch (e: Exception) {
                requireActivity().toast(e.localizedMessage)
                Log.e("TAG", "initUI: $e")
            }


        }
    }


    override fun onPurchasesUpdated(
        billingResult: BillingResult?,
        purchases: MutableList<com.android.billingclient.api.Purchase>?
    ) {
        if (billingResult?.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            val expert = purchases.firstOrNull { it.sku == "expert" }
            val intermediate = purchases.firstOrNull { it.sku == "intermediate" }

            if (expert?.purchaseState == com.android.billingclient.api.Purchase.PurchaseState.PURCHASED) {
                if (!expert.isAcknowledged) {
                    val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                        .setPurchaseToken(expert.purchaseToken)
                        .build()
                    billingClient.acknowledgePurchase(acknowledgePurchaseParams, this)

                    requireActivity().toast("expert purchased successfully!")
                }
            }

            if (intermediate?.purchaseState == com.android.billingclient.api.Purchase.PurchaseState.PURCHASED) {
                if (!intermediate.isAcknowledged) {
                    val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                        .setPurchaseToken(intermediate.purchaseToken)
                        .build()
                    billingClient.acknowledgePurchase(acknowledgePurchaseParams, this)

                    requireActivity().toast("intermediate purchased successfully!")
                }
            }

        } else if (billingResult?.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
            requireActivity().toast("${purchases?.firstOrNull()?.sku} purchased successfully!")

        } else if (billingResult?.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            requireActivity().toast("You cancelled!")
        } else {
            requireActivity().toast("error!")
        }
    }

    override fun onAcknowledgePurchaseResponse(billingResult: BillingResult?) {
        if (billingResult?.responseCode == BillingClient.BillingResponseCode.OK) {
            Log.d("TAG", "onAcknowledgePurchaseResponse: ${billingResult.debugMessage}")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (mBilling != null) {
            mBilling!!.onActivityResult(requestCode, resultCode, data);
        }
    }

}