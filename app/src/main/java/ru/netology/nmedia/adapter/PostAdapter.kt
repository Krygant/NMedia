package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post


typealias OnItemLikeListener = (post: Post) -> Unit
typealias OnItemShareListener = (post: Post) -> Unit//

class PostAdapter(
    private val onItemLikeListener: OnItemLikeListener,
    private val onItemShareListener: OnItemShareListener//
) : ListAdapter<Post, PostViewHolder>(PostDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onItemLikeListener, onItemShareListener)//
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onItemLikeListener: OnItemLikeListener,
    private val onItemShareListener: OnItemShareListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content

            val formattedShares = formatNumber(post.share)
            textShare.text = formattedShares
            val formattedLikes = formatNumber(post.likes)
            textLike.text = formattedLikes
            like.setImageResource(
                if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
            )
            like.setOnClickListener {
                onItemLikeListener(post)
            }
            share.setOnClickListener {//
                onItemShareListener(post)//
            }//
        }
    }
}

object PostDiffCallBack : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}

fun formatNumber(num: Int): String {
    if (num < 1_000) {
        return "$num"
    } else if (num < 10_000) {   // Обрабатываем числа меньше 10 тыс., показывая сотые доли
        return "${num / 1000}.${num % 1000 / 100}K"
    } else if (num < 1_000_000) {  // Обрабатываем числа между 10 тыс. и миллионом без дробной части
        return "${num / 1000}K"
    } else {                     // Числа свыше миллиона обрабатываются аналогично числам до 10 тыс.
        return "${num / 1_000_000}.${num % 1_000_000 / 100_000}M"
    }
}
