package ru.shmakova.vk.data.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IgnoreResponse(
    val response: Int
)
