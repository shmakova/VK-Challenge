package ru.shmakova.vk.domain.models

data class NewsFeed(
    val nextFrom: String,
    val items: List<NewsFeedItem>
)

data class NewsFeedItem(
    val profile: Profile,
    val date: Long,
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
