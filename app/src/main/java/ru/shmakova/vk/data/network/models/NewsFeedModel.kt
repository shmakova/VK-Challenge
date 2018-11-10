package ru.shmakova.vk.data.network.models

import com.google.gson.annotations.SerializedName

data class NewsFeedModel(
    val response: NewsFeedResponse
)

data class NewsFeedResponse(
    val items: List<NewsFeedItemResponse>,
    val profiles: List<NewsFeedProfile>,
    val groups: List<NewsFeedGroup>,
    @SerializedName("next_from") val nextFrom: String
)

data class NewsFeedItemResponse(
    @SerializedName("source_id") val sourceId: Long,
    @SerializedName("post_id") val postId: Long,
    val type: String,
    val date: Long,
    val text: String,
    val attachments: List<NewsFeedAttachment>?
)

data class NewsFeedAttachment(
    val type: String,
    val photo: NewsFeedAttachmentPhoto?
)

data class NewsFeedAttachmentPhoto(
    val sizes: List<NewsFeedAttachmentPhotoSize>
)

data class NewsFeedAttachmentPhotoSize(
    val type: String,
    val url: String
)

data class NewsFeedProfile(
    val id: Long,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("photo_50") val avatar: String
)

data class NewsFeedGroup(
    val id: Long,
    val name: String,
    @SerializedName("photo_50") val avatar: String
)
