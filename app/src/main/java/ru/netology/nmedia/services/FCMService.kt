package ru.netology.nmedia.services

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import ru.netology.nmedia.R
import kotlin.random.Random

class FCMService : FirebaseMessagingService() {

    private val action = "action"
    private val content = "content"
    private val gson = Gson()
    private val channelId = "remote"

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_remote_name)
            val descriptionText = getString(R.string.channel_remote_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.i("fcm msg", message.data.toString())
        message.data[action]?.let { action ->
            try {
                when (Action.valueOf(action)) {
                    Action.LIKE -> handleLike(
                        gson.fromJson(
                            message.data[content],
                            DeserializedPost::class.java
                        )
                    )

                    Action.NEW_POST -> handleNewPost(
                        gson.fromJson(
                            message.data[content],
                            DeserializedPost::class.java
                        )
                    )
                }
            } catch (iae: IllegalArgumentException) {
                Log.e("FCMService", "Unknown action received: $action")
            }
        }
    }

    private fun handleLike(like: DeserializedPost) {
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(
                getString(
                    R.string.notification_user_liked,
                    like.userName,
                    like.postAuthor
                )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        notify(notification)
    }

    private fun handleNewPost(nPost: DeserializedPost) {
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(
                getString(
                    R.string.notification_user_new_post,
                    nPost.userName
                )
            )
            .setContentText(
                nPost.postContent
            )
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(nPost.postContent)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        notify(notification)
    }

    private fun notify(notification: Notification) {
        val isUpperTiramisu = Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU
        val isPostNotificationGraded = if (isUpperTiramisu) {
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        } else true

        if (isPostNotificationGraded) {
            NotificationManagerCompat.from(this).notify(Random.nextInt(100_000), notification)
        }
    }

    enum class Action {
        LIKE,
        NEW_POST,
    }

    data class DeserializedPost(
        val userId: Long,
        val userName: String,
        val postId: Long,
        val postAuthor: String,
        val postContent: String
    )

    override fun onNewToken(token: String) {
        Log.i("fcm token", token)
    }
}