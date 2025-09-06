package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likes: Int = 9_999_999,
    val share: Int = 999,
    val likedByMe: Boolean = false,
    val shareByMe: Boolean = false,
    val video: String? = "",
    val address: String
) {
    fun toDto(): Post = Post(
        id = id,
        author = author,
        published = published,
        content = content,
        likes = likes,
        share = share,
        likedByMe = likedByMe,
        shareByMe = shareByMe,
        video = video,
        address = address
    )

    companion object {
        fun fromDto(post: Post): PostEntity = with(post) {
            PostEntity(
                id = id,
                author = author,
                published = published,
                content = content,
                likes = likes,
                share = share,
                likedByMe = likedByMe,
                shareByMe = shareByMe,
                video = video,
                address = address
            )
        }
    }
}

fun Post.toEntity(): PostEntity = PostEntity(
    id = id,
    author = author,
    published = published,
    content = content,
    likes = likes,
    share = share,
    likedByMe = likedByMe,
    shareByMe = shareByMe,
    video = video,
    address = address
)