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
            val newsItem = state.newsFeed.items.first()
            val profile = newsItem.profile
            contentCardView.setAvatar(profile.avatar)
            contentCardView.setName(profile.name)
            contentCardView.setDate("${newsItem.date}")
            contentCardView.setPhoto(newsItem.attachments.first().url)
            contentCardView.setText(newsItem.text)
        }
    }

    override fun onDestroy() {
        presenter.unbindView(this)
        super.onDestroy()
    }
}
