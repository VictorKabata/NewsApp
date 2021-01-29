package com.vickikbt.newsapp.repository

import android.util.Log
import com.vickikbt.newsapp.R
import com.vickikbt.newsapp.models.News
import com.vickikbt.newsapp.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(){

    fun getAllNews(): Flow<MutableList<News>> {
        val newsList= mutableListOf<News>()

        newsList.add(0, News(0, "A look into collaborative wire framing process.", R.string.news_content, R.drawable.news_image1,3, "19th Nov 2019", User(0, "John Lennon", R.drawable.intro_image)))
        newsList.add(1, News(1, "20 Tools that will help you become a better freelancer.", R.string.news_content, R.drawable.news_image2,4, "19th Nov 2019", User(0, "Kurt Cobain", R.drawable.intro_image)))
        newsList.add(2, News(2, "Collaboration work flows for remote design teams.", R.string.news_content, R.drawable.news_image3,6,"19th Nov 2019", User(0, "Kris Novaselik", R.drawable.intro_image)))
        newsList.add(3, News(3, "These are the songs designers listen to when they want to get in the zone.", R.string.news_content, R.drawable.news_image4,10,"19th Nov 2019", User(0, "Jimmy Hendix", R.drawable.intro_image)))

        return flow {emit(newsList) }
    }

    fun getNews(id:Int): Flow<News> {
        val news=News(0, "A look into collaborative wire framing process.", R.string.news_content, R.drawable.news_image1,3, "19th Nov 2019",User(0, "John Lennon", R.drawable.intro_image))

        return flow { emit(news) }
    }

}