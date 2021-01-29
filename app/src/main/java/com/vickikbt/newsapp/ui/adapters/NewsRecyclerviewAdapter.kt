package com.vickikbt.newsapp.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vickikbt.newsapp.R
import com.vickikbt.newsapp.databinding.ItemNewsBinding
import com.vickikbt.newsapp.models.News
import com.vickikbt.newsapp.ui.fragments.HomeFragmentDirections

class NewsRecyclerviewAdapter constructor(private val news: MutableList<News>) :
    RecyclerView.Adapter<NewsRecyclerviewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsRecyclerviewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemNewsBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_news, parent, false)
        return NewsRecyclerviewHolder(binding)
    }

    override fun getItemCount() = news.size

    override fun onBindViewHolder(holder: NewsRecyclerviewHolder, position: Int) {
        val news = news[position]
        holder.bind(holder.itemView.context, news)

        holder.itemView.setOnClickListener {
            val action=HomeFragmentDirections.homeToArticle(news.id)
            it.findNavController().navigate(action)
        }
    }
}

class NewsRecyclerviewHolder(private val binding: ItemNewsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(context: Context, news: News) {
        Glide.with(context).load(news.user.imageUrl).into(binding.imageViewUserProfile)
        binding.textViewUsername.text = news.user.userName
        binding.textViewArticleDuration.text = "${news.length} min read"
        binding.textViewArticleTitle.text = news.title
        Glide.with(context).load(news.imageUrl).into(binding.imageViewArticleImage)
    }

}