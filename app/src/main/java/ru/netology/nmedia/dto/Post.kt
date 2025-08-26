package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likes: Int = 9_999_999,
    val share: Int = 999,
    val likedByMe: Boolean = false,
    val shareByMe: Boolean = false,
    val video: String? = ""
)
