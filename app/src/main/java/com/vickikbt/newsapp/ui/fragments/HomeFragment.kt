package com.vickikbt.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.vickikbt.newsapp.R
import com.vickikbt.newsapp.databinding.FragmentHomeBinding
import com.vickikbt.newsapp.ui.adapters.HomeRecyclerviewAdapter
import com.vickikbt.newsapp.ui.adapters.NewsRecyclerviewAdapter
import com.vickikbt.newsapp.ui.viewmodels.NewsViewModel
import com.vickikbt.newsapp.util.StateListener
import com.vickikbt.newsapp.util.log
import com.vickikbt.newsapp.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), StateListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.home_to_intro)
        }

        initUI()

        return binding.root
    }

    private fun initUI() {
        /*viewModel.news.observe(viewLifecycleOwner, { news ->
            if (news.isNullOrEmpty()) requireActivity().toast("No news")

            binding.recyclerviewHome.adapter = NewsRecyclerviewAdapter(news)

        })*/

        val menuItems = mutableListOf<String>()
        menuItems.add("Basic expressions")
        menuItems.add("Row-level calculations")
        menuItems.add("Aggregate calculation")
        menuItems.add("Mathematical calculations")
        menuItems.add("Other expressions")

        binding.recyclerviewHome.adapter = HomeRecyclerviewAdapter(menuItems)
    }

    override fun onLoading() {

    }

    override fun onSuccess() {

    }

    override fun onFailure(message: String) {
        requireActivity().toast(message)
        requireActivity().log(message)
    }
}