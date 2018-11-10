package ru.shmakova.vk.presentation.main

import ru.shmakova.vk.domain.models.NewsFeed

data class MainViewState(
    val error: String? = null,
    val isLoading: Boolean = false,
    val newsFeed: NewsFeed? = null
)
