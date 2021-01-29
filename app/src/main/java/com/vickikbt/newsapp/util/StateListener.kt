package com.vickikbt.newsapp.util

interface StateListener {

    fun onLoading()

    fun onSuccess()

    fun onFailure(message:String)

}