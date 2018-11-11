package ru.shmakova.vk.presentation.news

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import ru.shmakova.vk.R
import ru.shmakova.vk.domain.models.Attachment

class NewsAttachmentsViewPagerAdapter constructor(private val attachments: List<Attachment>) :
    PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.item_news_attachment, container, false)
        val attachment: ImageView = view.findViewById(R.id.attachment)
        Glide.with(container.context)
            .load(attachments[position].url)
            .into(attachment)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj

    override fun getCount(): Int = attachments.size
}
