package com.vickikbt.newsapp.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vickikbt.newsapp.repository.NewsRepository
import com.vickikbt.newsapp.util.StateListener
import kotlinx.coroutines.flow.collect

class NewsDetailViewModel @ViewModelInject constructor(private val newsRepository: NewsRepository) :
    ViewModel() {

    var stateListener: StateListener? = null

    fun getNews(id: Int) = liveData {
        stateListener?.onLoading()

        try {
            val newsFlow = newsRepository.getNews(id)
            newsFlow.collect { news ->
                emit(news)
            }
            stateListener?.onSuccess()
            return@liveData
        } catch (e: Exception) {
            stateListener?.onFailure("${e.message}")
            return@liveData
        }

    }

}