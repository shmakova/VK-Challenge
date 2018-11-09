package ru.shmakova.vk.domain.interactors

import com.vk.sdk.VKAccessToken
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ru.shmakova.vk.data.network.VkApiService
import javax.inject.Inject

class MainInteractor @Inject constructor(
    private val apiService: VkApiService
) {
    fun getNewsFeed(): Observable<PartialMainViewState> {
        return apiService.getDiscoverForContestant(token = VKAccessToken.currentToken().accessToken)
            .toObservable()
            .subscribeOn(Schedulers.io())
            .map<PartialMainViewState> { PartialMainViewState.NewsFeedState(it) }
            .startWith(PartialMainViewState.LoadingState)
            .onErrorReturn { PartialMainViewState.ErrorState(it.message ?: "Unknown error") }
    }
}
