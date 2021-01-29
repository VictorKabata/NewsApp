package com.vickikbt.newsapp.ui.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vickikbt.newsapp.models.News
import com.vickikbt.newsapp.repository.NewsRepository
import com.vickikbt.newsapp.util.StateListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel @ViewModelInject constructor(private val newsRepository: NewsRepository) :
    ViewModel() {

    var stateListener: StateListener? = null

    private val _news = MutableLiveData<MutableList<News>>()
    val news: LiveData<MutableList<News>> = _news

    init {
        getAllNews()
    }

    private fun getAllNews() {
        stateListener?.onLoading()

        viewModelScope.launch {
            try {
                val newsFlow = newsRepository.getAllNews()
                newsFlow.collect { news ->
                    _news.value = news
                }
                stateListener?.onSuccess()
                return@launch
            } catch (e: Exception) {
                stateListener?.onFailure("${e.message}")
                return@launch
            }
        }
    }

}