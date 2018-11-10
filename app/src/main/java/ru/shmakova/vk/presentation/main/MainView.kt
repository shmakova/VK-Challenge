package ru.shmakova.vk.presentation.main

import io.reactivex.Observable

interface MainView {
    fun needUpdateIntent(): Observable<String>

    fun render(state: MainViewState)
}
