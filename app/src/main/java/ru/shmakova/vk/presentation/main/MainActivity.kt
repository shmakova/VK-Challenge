package ru.shmakova.vk.presentation.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import dagger.android.AndroidInjection
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import ru.shmakova.vk.R
import ru.shmakova.vk.domain.models.NewsFeedItem
import ru.shmakova.vk.presentation.news.NewsFeedAdapter
import swipeable.com.layoutmanager.OnItemSwiped
import swipeable.com.layoutmanager.SwipeableLayoutManager
import swipeable.com.layoutmanager.SwipeableTouchHelperCallback
import swipeable.com.layoutmanager.touchelper.ItemTouchHelper
import timber.log.Timber
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainView, OnItemSwiped {

    private lateinit var newsFeedStackView: RecyclerView
    private lateinit var likeButton: ImageView
    private lateinit var skipButton: ImageView
    private val adapter = NewsFeedAdapter()

    private val swipableTouchHelperCallback = SwipeableTouchHelperCallback(this)

    private val needUpdateSubject: PublishSubject<String> = PublishSubject.create()
    private val likeSubject: PublishSubject<NewsFeedItem> = PublishSubject.create()
    private val skipSubject: PublishSubject<NewsFeedItem> = PublishSubject.create()

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newsFeedStackView = findViewById(R.id.news_feed_stack_view)
        likeButton = findViewById(R.id.like_button)
        skipButton = findViewById(R.id.skip_button)
        likeButton.setOnClickListener {
            adapter.removeTopItem()
        }
        skipButton.setOnClickListener {
            adapter.removeTopItem()
        }
        val layoutManager = SwipeableLayoutManager()
        layoutManager.setAngle(10)
            .setAnimationDuratuion(300)
            .setMaxShowCount(3)
            .setScaleGap(0.25f)
            .setTransYGap(0)
        val itemTouchHelper = ItemTouchHelper(swipableTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(newsFeedStackView)
        newsFeedStackView.layoutManager = layoutManager
        newsFeedStackView.adapter = adapter
        presenter.bindView(this)
        needUpdateSubject.onNext("")
    }

    override fun onItemSwiped() {
        Timber.e("onItemSwiped")
    }

    override fun onItemSwipedRight() {
        Timber.e("onItemSwipedRight")
        val newsFeedItem = adapter.getTopItem() ?: return
        likeSubject.onNext(newsFeedItem)
        handleSwipe()
    }

    override fun onItemSwipedDown() {
        Timber.e("onItemSwipedDown")
    }

    override fun onItemSwipedUp() {
        Timber.e("onItemSwipedUp")
    }

    override fun onItemSwipedLeft() {
        Timber.e("onItemSwipedLeft")
        val newsFeedItem = adapter.getTopItem() ?: return
        skipSubject.onNext(newsFeedItem)
        handleSwipe()
    }

    override fun needUpdateIntent(): Observable<String> {
        return needUpdateSubject
    }

    override fun skipIntent(): Observable<NewsFeedItem> {
        return skipSubject
    }

    override fun likeIntent(): Observable<NewsFeedItem> {
        return likeSubject
    }

    override fun render(state: MainViewState) {
        Timber.e("Render state: %s", state)
        if (state.newsFeed != null) {
            adapter.appendNewsFeed(state.newsFeed)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        presenter.unbindView(this)
        super.onDestroy()
    }

    private fun handleSwipe() {
        adapter.removeTopItem()
        if (adapter.itemCount < MIN_COUNT_TO_UPDATE) {
            needUpdateSubject.onNext(adapter.getNextFrom())
        }
    }
}

private const val MIN_COUNT_TO_UPDATE = 5
