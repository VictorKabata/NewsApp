package com.vickikbt.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.vickikbt.newsapp.R
import com.vickikbt.newsapp.databinding.FragmentHomeBinding
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
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        initUI()

        return binding.root
    }

    private fun initUI() {
        viewModel.news.observe(viewLifecycleOwner, { news ->
            if (news.isNullOrEmpty()) requireActivity().toast("No news")

            binding.recyclerviewHome.adapter = NewsRecyclerviewAdapter(news)

        })
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