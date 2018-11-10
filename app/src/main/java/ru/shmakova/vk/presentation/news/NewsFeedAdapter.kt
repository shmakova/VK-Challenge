package ru.shmakova.vk.presentation.news

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.shmakova.vk.R
import ru.shmakova.vk.domain.models.NewsFeedItem

class NewsFeedAdapter : RecyclerView.Adapter<NewsViewHolder>() {
    private var items: MutableList<NewsFeedItem> = mutableListOf()

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
        holder.newsCardView.setNewsFeedItem(items[position])
    }

    override fun getItemCount() = items.size

    fun setItems(items: MutableList<NewsFeedItem>) {
        this.items = items
    }

    fun removeTopItem() {
        items.removeAt(0)
        notifyItemRemoved(0)
    }
}

class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val newsCardView: NewsCardView = view.findViewById(R.id.news_card)
}
