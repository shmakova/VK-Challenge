package ru.shmakova.vk.data.network.models

import com.google.gson.annotations.SerializedName

data class NewsFeed(
    val response: NewsFeedResponse
)

data class NewsFeedResponse(
    val items: List<NewsFeedItem>,
    val profiles: List<NewsFeedProfile>,
    @SerializedName("next_from") val nextFrom: String
)

data class NewsFeedItem(
    @SerializedName("source_id") val sourceId: Long,
    val date: Long,
    val text: String
)

data class NewsFeedProfile(
    @SerializedName("id") val id: Long,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("photo_50") val photo: String
)
