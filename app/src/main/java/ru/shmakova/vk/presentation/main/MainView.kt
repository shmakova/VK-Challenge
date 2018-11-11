package ru.shmakova.vk.presentation.main

import io.reactivex.Observable
import ru.shmakova.vk.domain.models.NewsFeedItem

interface MainView {
    fun needUpdateIntent(): Observable<String>

    fun likeIntent(): Observable<NewsFeedItem>

    fun skipIntent(): Observable<NewsFeedItem>

    fun retryClickIntent(): Observable<Any>

    fun render(state: MainViewState)
}
