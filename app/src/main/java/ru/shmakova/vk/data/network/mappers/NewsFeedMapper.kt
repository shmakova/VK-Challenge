package ru.shmakova.vk.data.network.mappers

import ru.shmakova.vk.data.network.models.NewsFeedModel
import ru.shmakova.vk.domain.models.Attachment
import ru.shmakova.vk.domain.models.NewsFeed
import ru.shmakova.vk.domain.models.NewsFeedItem
import ru.shmakova.vk.domain.models.Profile
import java.util.*
import java.util.concurrent.TimeUnit

fun NewsFeedModel.toNewsFeed(): NewsFeed {
    val newsFeedItems = mutableListOf<NewsFeedItem>()
    val response = this.response
    val profiles = response.profiles
    val groups = response.groups
    val profileMap = mutableMapOf<Long, Profile>()
    profiles.forEach {
        profileMap[it.id] = Profile(
            id = it.id,
            name = "${it.firstName} ${it.lastName}",
            avatar = it.avatar
        )
    }
    groups.forEach {
        profileMap[it.id] = Profile(
            id = it.id,
            name = it.name,
            avatar = it.avatar
        )
    }
    val items = response.items
    items.forEach {
        val newsFeedAttachments = it.attachments
        val attachments = mutableListOf<Attachment>()
        newsFeedAttachments?.forEach {
            val sizes = it.photo?.sizes
            val xSizes = sizes?.filter { size -> size.type == "x" }
            val url = xSizes?.first()?.url
            if (url != null) {
                attachments.add(Attachment(type = it.type, url = url))
            }
            val videoUrl = it.video?.url
            if (videoUrl != null) {
                attachments.add(Attachment(type = it.type, url = videoUrl))
            }
        }
        val profile = profileMap[Math.abs(it.sourceId)]
        if (profile != null) {
            newsFeedItems.add(
                NewsFeedItem(
                    profile = profile,
                    type = it.type,
                    sourceId = it.sourceId,
                    postId = it.postId,
                    date = Date(TimeUnit.SECONDS.toMillis(it.date)),
                    text = it.text,
                    attachments = attachments
                )
            )
        }
    }
    return NewsFeed(nextFrom = this.response.nextFrom, items = newsFeedItems)
}
