package ru.shmakova.vk.domain.interactors

import ru.shmakova.vk.domain.models.NewsFeed

sealed class PartialMainViewState {
    object LoadingState : PartialMainViewState()

    data class NewsFeedState(val newsFeed: NewsFeed) : PartialMainViewState()

    data class ErrorState(val error: String) : PartialMainViewState()
}
