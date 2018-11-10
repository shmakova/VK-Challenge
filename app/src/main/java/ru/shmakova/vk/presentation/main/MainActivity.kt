package ru.shmakova.vk.presentation.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import dagger.android.AndroidInjection
import ru.shmakova.vk.R
import ru.shmakova.vk.presentation.news.NewsFeedAdapter
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var newsFeedStackView: RecyclerView
    private val adapter = NewsFeedAdapter()

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newsFeedStackView = findViewById(R.id.news_feed_stack_view)
        newsFeedStackView.layoutManager = LinearLayoutManager(this)
        newsFeedStackView.adapter = adapter
        presenter.bindView(this)
    }

    override fun render(state: MainViewState) {
        Timber.e("Render state: %s", state)
        if (state.newsFeed != null) {
            adapter.setItems(state.newsFeed.items)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        presenter.unbindView(this)
        super.onDestroy()
    }
}
