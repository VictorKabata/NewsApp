package com.vickikbt.newsapp.models

data class News(
    val id: Int,
    val title: String,
    val content: Int,
    val imageUrl: Int,
    val length: Int,
    val createdAt:String,
    val user: User
)