package ru.shmakova.vk.domain.models

import java.util.*

data class NewsFeed(
    val nextFrom: String,
    val items: MutableList<NewsFeedItem>
)

data class NewsFeedItem(
    val profile: Profile,
    val type: String,
    val sourceId: Long,
    val postId: Long,
    val date: Date,
    val text: String,
    val attachments: List<Attachment>
)

data class Profile(
    val id: Long,
    val name: String,
    val avatar: String
)

data class Attachment(
    val type: String,
    val url: String
)
