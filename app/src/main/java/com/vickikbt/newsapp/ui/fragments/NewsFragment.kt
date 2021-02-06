package com.vickikbt.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.vickikbt.newsapp.R
import com.vickikbt.newsapp.databinding.FragmentNewsBinding
import com.vickikbt.newsapp.ui.viewmodels.NewsDetailViewModel
import com.vickikbt.newsapp.util.UserSession
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val viewModel: NewsDetailViewModel by activityViewModels()
    private val args: NewsFragmentArgs by navArgs()

    private lateinit var userSession: UserSession

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)

        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        userSession = UserSession(requireActivity())

        binding.buttonUpgrade.setOnClickListener {
            findNavController().navigate(R.id.news_to_subscription)
        }

        initUI()

        return binding.root
    }

    private fun initUI() {
        viewModel.getNews(args.NewsId).observe(viewLifecycleOwner, { news ->
            Glide.with(requireActivity()).load(news.imageUrl).into(binding.imageViewNews)
            binding.textViewNewsTitle.text = news.title
            Glide.with(requireActivity()).load(news.user.imageUrl)
                .into(binding.imageViewUserProfile)
            binding.textViewUsername.text = news.user.userName
            binding.textViewArticleDate.text = news.createdAt
            //binding.textViewNewsContent.text=news.content TODO: Replace with data from data source
            binding.textViewNewsContent.text = getString(R.string.news_content)

            if (userSession.isUserSubscribed()) binding.buttonUpgrade.visibility = View.GONE
            else {
                binding.buttonUpgrade.visibility = View.VISIBLE
                binding.textViewNewsContent.maxLines = 5
            }
        })
    }
}