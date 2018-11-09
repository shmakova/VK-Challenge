package ru.shmakova.vk.presentation.main

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.shmakova.vk.domain.interactors.MainInteractor
import ru.shmakova.vk.domain.interactors.PartialMainViewState
import ru.shmakova.vk.presentation.base.BasePresenter
import javax.inject.Inject

class MainPresenter @Inject constructor(private val mainInteractor: MainInteractor) :
    BasePresenter<MainView>(MainView::class.java) {

    override fun bindView(view: MainView) {
        super.bindView(view)
        bindIntents()
    }

    private fun bindIntents() {
        val newsFeedIntent: Observable<PartialMainViewState> = mainInteractor.getNewsFeed()

        val allIntentsObservable = newsFeedIntent.observeOn(AndroidSchedulers.mainThread())

        val initialState = MainViewState()

        unsubscribeOnUnbindView(
            allIntentsObservable.scan(initialState, this::viewStateReducer).distinctUntilChanged()
                .subscribe { state -> view().render(state) }
        )
    }

    private fun viewStateReducer(
        previousState: MainViewState,
        partialChanges: PartialMainViewState
    ): MainViewState {
        return when (partialChanges) {
            is PartialMainViewState.LoadingState -> previousState.copy(
                isLoading = true,
                error = null,
                newsFeed = null
            )
            is PartialMainViewState.ErrorState -> previousState.copy(
                isLoading = false,
                error = partialChanges.error,
                newsFeed = null
            )
            is PartialMainViewState.NewsFeedState -> previousState.copy(
                isLoading = false,
                error = null,
                newsFeed = partialChanges.newsFeed
            )
        }
    }
}
