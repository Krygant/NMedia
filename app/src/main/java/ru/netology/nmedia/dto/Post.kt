package ru.netology.nmedia.dto

data class Post(
    val id: Int,
    val author: String,
    val published: String,
    val content: String,
    var like: Int = 1_000_000,
    var share: Int = 99_999_999,
    var likeByMe: Boolean = false
)
