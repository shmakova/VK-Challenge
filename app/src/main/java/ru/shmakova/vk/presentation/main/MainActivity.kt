package ru.shmakova.vk.presentation.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.widget.Button
import dagger.android.AndroidInjection
import ru.shmakova.vk.R
import ru.shmakova.vk.presentation.news.NewsFeedAdapter
import swipeable.com.layoutmanager.OnItemSwiped
import swipeable.com.layoutmanager.SwipeableLayoutManager
import swipeable.com.layoutmanager.SwipeableTouchHelperCallback
import swipeable.com.layoutmanager.touchelper.ItemTouchHelper
import timber.log.Timber
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainView, OnItemSwiped {

    private lateinit var newsFeedStackView: RecyclerView
    private lateinit var likeButton: Button
    private lateinit var skipButton: Button
    private val adapter = NewsFeedAdapter()

    private val swipableTouchHelperCallback = SwipeableTouchHelperCallback(this)

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newsFeedStackView = findViewById(R.id.news_feed_stack_view)
        val layoutManager = SwipeableLayoutManager()
        layoutManager.setAngle(10)
            .setAnimationDuratuion(300)
            .setMaxShowCount(3)
            .setScaleGap(0.1f)
            .setTransYGap(0)
        val itemTouchHelper = ItemTouchHelper(swipableTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(newsFeedStackView)
        newsFeedStackView.layoutManager = layoutManager
        newsFeedStackView.adapter = adapter
        presenter.bindView(this)
    }

    override fun onItemSwiped() {
        Timber.e("onItemSwiped")
        adapter.removeTopItem()
    }

    override fun onItemSwipedRight() {
        Timber.e("onItemSwipedRight")
    }

    override fun onItemSwipedDown() {
        Timber.e("onItemSwipedDown")
    }

    override fun onItemSwipedUp() {
        Timber.e("onItemSwipedUp")
    }

    override fun onItemSwipedLeft() {
        Timber.e("onItemSwipedLeft")
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
