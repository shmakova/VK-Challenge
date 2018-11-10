package ru.shmakova.vk.presentation.news

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.shmakova.vk.R
import ru.shmakova.vk.domain.models.NewsFeed
import swipeable.com.layoutmanager.OnItemSwipePercentageListener

class NewsFeedAdapter : RecyclerView.Adapter<NewsViewHolder>() {

    private var newsFeed: NewsFeed = NewsFeed(nextFrom = "", items = mutableListOf())

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder = NewsViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.news_item,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.newsCardView.showNewsFeedItem(newsFeed.items[position])
    }

    override fun getItemCount() = newsFeed.items.size

    fun getNextFrom() = newsFeed.nextFrom

    fun getTopItem() = newsFeed.items.firstOrNull()

    fun appendNewsFeed(newsFeed: NewsFeed) {
        this.newsFeed.items.addAll(newsFeed.items)
        this.newsFeed = this.newsFeed.copy(nextFrom = newsFeed.nextFrom)
        notifyDataSetChanged()
    }

    fun removeTopItem() {
        newsFeed.items.removeAt(0)
        notifyDataSetChanged()
    }
}

class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view), OnItemSwipePercentageListener {

    val newsCardView: NewsCardView = view.findViewById(R.id.news_card)

    override fun onItemSwipePercentage(percentage: Double) {
        if (percentage > 0) {
            newsCardView.showLikeBadge()
            newsCardView.hideSkipBadge()
        } else if (percentage < 0) {
            newsCardView.showSkipBadge()
            newsCardView.hideLikeBadge()
        } else {
            newsCardView.hideLikeBadge()
            newsCardView.hideSkipBadge()
        }
    }
}
