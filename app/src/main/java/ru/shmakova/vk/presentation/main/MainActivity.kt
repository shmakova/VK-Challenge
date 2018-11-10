package ru.shmakova.vk.presentation.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import ru.shmakova.vk.R
import ru.shmakova.vk.presentation.card.ContentCardView
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var contentCardView: ContentCardView

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        contentCardView = findViewById(R.id.card)
        presenter.bindView(this)
    }

    override fun render(state: MainViewState) {
        Timber.e("Render state: %s", state)
        if (state.newsFeed != null) {
            val news = state.newsFeed.response.items.first()
            val profile = state.newsFeed.response.profiles.first()
            contentCardView.setAvatar(profile.photo)
            contentCardView.setName("${profile.firstName} ${profile.lastName}")
            contentCardView.setDate("${news.date}")
            contentCardView.setText(news.text)
        }
    }

    override fun onDestroy() {
        presenter.unbindView(this)
        super.onDestroy()
    }
}
