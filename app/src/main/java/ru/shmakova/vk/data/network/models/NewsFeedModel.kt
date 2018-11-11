package ru.shmakova.vk.data.network.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsFeedModel(
    val response: NewsFeedResponse
)

@JsonClass(generateAdapter = true)
data class NewsFeedResponse(
    val items: List<NewsFeedItemResponse>,
    val profiles: List<NewsFeedProfile>,
    val groups: List<NewsFeedGroup>,
    @Json(name = "next_from") val nextFrom: String
)

@JsonClass(generateAdapter = true)
data class NewsFeedItemResponse(
    @Json(name = "source_id") val sourceId: Long,
    @Json(name = "post_id") val postId: Long,
    val type: String,
    val date: Long,
    val text: String,
    val attachments: List<NewsFeedAttachment>?
)

@JsonClass(generateAdapter = true)
data class NewsFeedAttachment(
    val type: String,
    val photo: NewsFeedAttachmentPhoto?
)

@JsonClass(generateAdapter = true)
data class NewsFeedAttachmentPhoto(
    val sizes: List<NewsFeedAttachmentPhotoSize>
)

@JsonClass(generateAdapter = true)
data class NewsFeedAttachmentPhotoSize(
    val type: String,
    val url: String
)

@JsonClass(generateAdapter = true)
data class NewsFeedProfile(
    val id: Long,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    @Json(name = "photo_50") val avatar: String
)

@JsonClass(generateAdapter = true)
data class NewsFeedGroup(
    val id: Long,
    val name: String,
    @Json(name = "photo_50") val avatar: String
)
