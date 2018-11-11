package ru.shmakova.vk.presentation.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
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
    private lateinit var loadingView: View
    private lateinit var retryButton: Button
    private lateinit var errorTextView: TextView
    private val adapter = NewsFeedAdapter()

    private val swipeableTouchHelperCallback = SwipeableTouchHelperCallback(this)

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
        loadingView = findViewById(R.id.loading_view)
        retryButton = findViewById(R.id.retry_button)
        errorTextView = findViewById(R.id.error)
        likeButton.setOnClickListener {
            like()
        }
        skipButton.setOnClickListener {
            skip()
        }
        val layoutManager = SwipeableLayoutManager()
        layoutManager.setAngle(DEFAULT_ANGLE)
            .setAnimationDuratuion(DEFAULT_ANIMATION_DURATION)
            .setMaxShowCount(MAX_SHOW_COUNT)
            .setScaleGap(SCALE_GAP)
            .setTransYGap(0)
        val itemTouchHelper = ItemTouchHelper(swipeableTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(newsFeedStackView)
        newsFeedStackView.layoutManager = layoutManager
        newsFeedStackView.adapter = adapter
        presenter.bindView(this)
        needUpdateSubject.onNext("")
    }

    override fun onItemSwiped() {
        Timber.d("onItemSwiped")
    }

    override fun onItemSwipedRight() {
        Timber.d("onItemSwipedRight")
        like()
    }

    override fun onItemSwipedDown() {
        Timber.d("onItemSwipedDown")
    }

    override fun onItemSwipedUp() {
        Timber.d("onItemSwipedUp")
    }

    override fun onItemSwipedLeft() {
        Timber.d("onItemSwipedLeft")
        skip()
    }

    override fun needUpdateIntent(): Observable<String> = needUpdateSubject

    override fun skipIntent(): Observable<NewsFeedItem> = skipSubject

    override fun likeIntent(): Observable<NewsFeedItem> = likeSubject

    override fun retryClickIntent(): Observable<Any> = RxView.clicks(retryButton)

    override fun render(state: MainViewState) {
        Timber.d("Render state: %s", state)
        if (state.newsFeed != null) {
            loadingView.visibility = View.GONE
            errorTextView.visibility = View.GONE
            retryButton.visibility = View.GONE
            adapter.appendNewsFeed(state.newsFeed)
            adapter.notifyDataSetChanged()
        } else if (state.error != null) {
            loadingView.visibility = View.GONE
            errorTextView.setText(R.string.internet_connection_error)
            errorTextView.visibility = View.VISIBLE
            retryButton.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        presenter.unbindView(this)
        super.onDestroy()
    }

    private fun like() {
        val newsFeedItem = adapter.getTopItem() ?: return
        likeSubject.onNext(newsFeedItem)
        handleRemove()
    }

    private fun skip() {
        val newsFeedItem = adapter.getTopItem() ?: return
        skipSubject.onNext(newsFeedItem)
        handleRemove()
    }

    private fun handleRemove() {
        adapter.removeTopItem()
        if (adapter.itemCount < MIN_COUNT_TO_UPDATE) {
            needUpdateSubject.onNext(adapter.getNextFrom())
        }
    }
}

private const val MIN_COUNT_TO_UPDATE = 5
private const val DEFAULT_ANGLE = 10
private const val DEFAULT_ANIMATION_DURATION = 300L
private const val MAX_SHOW_COUNT = 3
private const val SCALE_GAP = 0.25f
