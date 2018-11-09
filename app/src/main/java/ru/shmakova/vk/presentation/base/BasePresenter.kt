package ru.shmakova.vk.presentation.base

import android.support.annotation.CallSuper

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<V : Any>(private val viewClass: Class<V>) {

    private val subscriptionsToUnsubscribeOnUnbindView = CompositeDisposable()

    @Volatile
    private var view: V? = null

    @CallSuper
    protected open fun bindView(view: V) {
        val previousView = this.view

        if (previousView != null) {
            throw IllegalStateException("Previous view is not unbound! previousView = $previousView")
        }

        this.view = view
    }

    protected fun view(): V {
        return checkNotNull(view) { "You should bind before accessing view!!" }
    }

    protected fun unsubscribeOnUnbindView(disposable: Disposable, vararg disposables: Disposable) {
        subscriptionsToUnsubscribeOnUnbindView.add(disposable)

        for (d in disposables) {
            subscriptionsToUnsubscribeOnUnbindView.add(d)
        }
    }

    @CallSuper
    open fun unbindView(view: V) {
        val previousView = this.view

        if (previousView === view) {
            this.view = null
        } else {
            throw IllegalStateException("Unexpected view! previousView = $previousView, view to unbind = $view")
        }

        // Unsubscribe all subscriptions that need to be unsubscribed in this lifecycle state.
        subscriptionsToUnsubscribeOnUnbindView.clear()
    }

    fun isViewBound(view: V): Boolean {
        return this.view === view
    }
}
