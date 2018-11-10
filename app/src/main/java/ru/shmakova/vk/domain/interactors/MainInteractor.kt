package ru.shmakova.vk.domain.interactors

import com.vk.sdk.VKAccessToken
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ru.shmakova.vk.data.network.VkApiService
import ru.shmakova.vk.data.network.mappers.toNewsFeed
import ru.shmakova.vk.domain.models.NewsFeedItem
import timber.log.Timber
import javax.inject.Inject

class MainInteractor @Inject constructor(
    private val apiService: VkApiService
) {
    fun getNewsFeed(nextFrom: String): Observable<PartialMainViewState> {
        return apiService.getDiscoverForContestant(
            token = VKAccessToken.currentToken().accessToken,
            startFrom = if (nextFrom.isEmpty()) null else nextFrom
        ).toObservable()
            .map { it -> it.toNewsFeed() }
            .subscribeOn(Schedulers.io())
            .map<PartialMainViewState> { PartialMainViewState.NewsFeedState(it) }
            .startWith(PartialMainViewState.LoadingState)
            .onErrorReturn {
                Timber.e(it)
                PartialMainViewState.ErrorState(it.message ?: "Unknown error")
            }
    }

    fun skip(item: NewsFeedItem): Observable<PartialMainViewState> {
        return apiService.ignoreItem(
            token = VKAccessToken.currentToken().accessToken,
            ownerId = item.sourceId,
            itemId = item.postId
        ).toObservable()
            .subscribeOn(Schedulers.io())
            .map<PartialMainViewState> { PartialMainViewState.SkipNewsSuccessState }
            .onErrorReturn {
                Timber.e(it)
                PartialMainViewState.SkipNewsSuccessState
            }
    }

    fun like(item: NewsFeedItem): Observable<PartialMainViewState> {
        return apiService.likeItem(
            token = VKAccessToken.currentToken().accessToken,
            type = item.type,
            ownerId = item.sourceId,
            itemId = item.postId
        ).toObservable()
            .subscribeOn(Schedulers.io())
            .map<PartialMainViewState> { PartialMainViewState.LikeNewsSuccessState }
            .onErrorReturn {
                Timber.e(it)
                PartialMainViewState.LikeNewsSuccessState
            }
    }
}
