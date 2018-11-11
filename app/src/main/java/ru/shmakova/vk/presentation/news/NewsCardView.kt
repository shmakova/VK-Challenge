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
import ru.shmakova.vk.domain.models.Attachment
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
    private val likeBadge: View
    private val skipBadge: View

    init {
        inflate(context, R.layout.news_card_view, this)
        avatarView = findViewById(R.id.avatar)
        nameView = findViewById(R.id.name)
        dateView = findViewById(R.id.date)
        photoView = findViewById(R.id.attachment)
        textView = findViewById(R.id.text)
        likeBadge = findViewById(R.id.like_badge)
        skipBadge = findViewById(R.id.skip_badge)
    }

    fun showNewsFeedItem(newsItem: NewsFeedItem) {
        val profile = newsItem.profile
        showAvatar(profile.avatar)
        showName(profile.name)
        showDate(newsItem.date)
        showAttachments(newsItem.attachments)
        showText(newsItem.text)
        hideLikeBadge()
        hideSkipBadge()
    }

    fun showLikeBadge() {
        likeBadge.visibility = View.VISIBLE
    }

    fun hideLikeBadge() {
        likeBadge.visibility = View.GONE
    }

    fun showSkipBadge() {
        skipBadge.visibility = View.VISIBLE
    }

    fun hideSkipBadge() {
        skipBadge.visibility = View.GONE
    }

    private fun showAvatar(url: String) {
        Glide.with(this)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(avatarView)
    }

    private fun showName(name: String) {
        nameView.text = name
    }

    private fun showDate(date: Date) {
        dateView.text = resources.getString(
            R.string.news_card_date,
            FormatUtils.getRelativeDayFromDate(date),
            FormatUtils.getTimeFromDate(context, date)
        )
    }

    private fun showAttachments(attachments: List<Attachment>) {
        val photo = attachments.firstOrNull()
        if (photo == null) {
            photoView.visibility = GONE
        } else {
            photoView.visibility = View.VISIBLE
            Glide.with(this)
                .load(photo.url)
                .into(photoView)
        }
    }

    private fun showText(text: String) {
        if (!text.isEmpty()) {
            textView.visibility = View.VISIBLE
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
}
