package ru.netology.nmedia.dto

data class Post(
    val id: Int,
    val author: String,
    val published: String,
    val content: String,
    val like: Int = 9_999_999,
    val share: Int = 999,
    val likeByMe: Boolean = false
)
