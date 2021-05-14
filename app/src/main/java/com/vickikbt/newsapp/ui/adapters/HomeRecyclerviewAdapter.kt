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
import com.vickikbt.newsapp.databinding.ItemMenuBinding
import com.vickikbt.newsapp.databinding.ItemNewsBinding
import com.vickikbt.newsapp.models.News
import com.vickikbt.newsapp.ui.fragments.HomeFragmentDirections

class HomeRecyclerviewAdapter constructor(private val menu: MutableList<String>) :
    RecyclerView.Adapter<HomeRecyclerviewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerviewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemMenuBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_menu, parent, false)
        return HomeRecyclerviewHolder(binding)
    }

    override fun getItemCount() = menu.size

    override fun onBindViewHolder(holder: HomeRecyclerviewHolder, position: Int) {
        val menuItem = menu[position]
        holder.bind(menuItem)

        /*holder.itemView.setOnClickListener {
            val action=HomeFragmentDirections.homeToArticle(news.id)
            it.findNavController().navigate(action)
        }*/
    }
}

class HomeRecyclerviewHolder(private val binding: ItemMenuBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(menuItem:String) {
        binding.textViewMenuItem.text=menuItem

        /*Glide.with(context).load(news.user.imageUrl).into(binding.imageViewUserProfile)
        binding.textViewUsername.text = news.user.userName
        binding.textViewArticleDuration.text = "${news.length} min read"
        binding.textViewArticleTitle.text = news.title
        Glide.with(context).load(news.imageUrl).into(binding.imageViewArticleImage)*/
    }

}