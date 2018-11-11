package ru.shmakova.vk.data.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LikeResponse(
    val response: Likes
)

@JsonClass(generateAdapter = true)
data class Likes(
    val likes: Int
)
