package ru.netology.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb"

        )

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            like.setImageResource(R.drawable.ic_like_24)

            like.setOnClickListener {
                post.likeByMe = !post.likeByMe
                if (post.likeByMe) {
                    like.setImageResource(R.drawable.ic_liked_24)
                    textLike.text = formatNumber(post.like--)
                } else {
                    like.setImageResource(R.drawable.ic_like_24)
                    textLike.text = formatNumber(post.like++)
                }

            }

            share.setOnClickListener{
                textShare.text = formatNumber(post.share++)
            }
        }
    }

    fun formatNumber(num: Int): String {
        if (num < 1_000){
            return "$num"
        } else if (num < 10_000) {   // Обрабатываем числа меньше 10 тыс., показывая сотые доли
            return "${num / 1000}.${num % 1000 / 100}K"
        } else if (num < 1_000_000) {  // Обрабатываем числа между 10 тыс. и миллионом без дробной части
            return "${num / 1000}K"
        } else {                     // Числа свыше миллиона обрабатываются аналогично числам до 10 тыс.
            return "${num / 1_000_000}.${num % 1_000_000 / 100_000}M"
        }
    }
}