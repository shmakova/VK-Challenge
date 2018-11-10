package ru.shmakova.vk.presentation.news

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.shmakova.vk.R
import ru.shmakova.vk.domain.models.NewsFeedItem
import ru.shmakova.vk.presentation.utils.FormatUtils
import java.util.*


class NewsCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val avatarView: ImageView
    private val nameView: TextView
    private val dateView: TextView
    private val photoView: ImageView
    private val textView: TextView

    init {
        inflate(context, R.layout.news_card_view, this)
        avatarView = findViewById(R.id.avatar)
        nameView = findViewById(R.id.name)
        dateView = findViewById(R.id.date)
        photoView = findViewById(R.id.attachment)
        textView = findViewById(R.id.text)
    }

    fun setNewsFeedItem(newsItem: NewsFeedItem) {
        val profile = newsItem.profile
        setAvatar(profile.avatar)
        setName(profile.name)
        setDate(newsItem.date)
        val photo = newsItem.attachments.firstOrNull()
        if (photo == null) {
            photoView.visibility = GONE
        } else {
            photoView.visibility = View.VISIBLE
            setPhoto(photo.url)
        }
        setText(newsItem.text)
    }

    private fun setAvatar(url: String) {
        Glide.with(this)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(avatarView)
    }

    private fun setName(name: String) {
        nameView.text = name
    }

    private fun setDate(date: Date) {
        dateView.text = resources.getString(
            R.string.news_card_date,
            FormatUtils.getRelativeDayFromDate(date),
            FormatUtils.getTimeFromDate(context, date)
        )
    }

    private fun setPhoto(url: String) {
        Glide.with(this)
            .load(url)
            .apply(RequestOptions.centerCropTransform())
            .into(photoView)
    }

    private fun setText(text: String) {
        textView.text = text
        textView.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    viewTreeObserver.removeOnPreDrawListener(this)
                    val maxLines = textView.height / textView.lineHeight
                    textView.maxLines = maxLines
                    return true
                }
            })
    }
}
