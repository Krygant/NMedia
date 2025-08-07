package ru.netology.nmedia.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("MainActivity", "onCreate")
        Log.d("MainActivity", "MainActivity hashCode ${this.hashCode()}")

        val viewModel: PostViewModel by viewModels()
        Log.d("MainActivity", "viewModel hashCode ${viewModel.hashCode()}")

        viewModel.data.observe(this) { post ->
            Log.d("MainActivity", "Post $post")
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                like.setImageResource(R.drawable.ic_like_24)
                if (post.likeByMe) {
                    like.setImageResource(R.drawable.ic_liked_24)
                } else {
                    like.setImageResource(R.drawable.ic_like_24)
                }
                textLike.text = formatNumber(post.like)
                textShare.text = formatNumber(post.share)
            }
        }

        binding.like.setOnClickListener {
            viewModel.like()
        }


        binding.share.setOnClickListener {
            viewModel.share()
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

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("MainActivity", "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause")
    }
}