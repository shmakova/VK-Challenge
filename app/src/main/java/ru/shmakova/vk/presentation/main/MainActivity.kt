package ru.shmakova.vk.presentation.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import ru.shmakova.vk.R
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.bindView(this)
    }

    override fun render(state: MainViewState) {
        Timber.e("Render state: %s", state)
    }

    override fun onDestroy() {
        presenter.unbindView(this)
        super.onDestroy()
    }
}
