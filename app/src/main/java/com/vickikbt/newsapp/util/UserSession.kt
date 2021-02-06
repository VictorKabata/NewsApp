package com.vickikbt.newsapp.util

import android.content.Context

class UserSession constructor(private val context: Context) {

    private val IsSubscribe = "IsSubscribe"
    private val subscribePrefs = context.getSharedPreferences("SubscribePrefs", 0)

    fun setUserSubscribed(isSubscribe: Boolean) {
        val editor = subscribePrefs.edit()
        editor.putBoolean(IsSubscribe, isSubscribe)
        editor.apply()
    }

    fun isUserSubscribed() = subscribePrefs.getBoolean(IsSubscribe, false)


}