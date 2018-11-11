package ru.shmakova.vk.presentation.news

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.view.ViewPager
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
    private val attachmentsViewPager: ViewPager
    private val attachmentsView: View
    private val textView: TextView
    private val likeBadge: View
    private val skipBadge: View
    private val prevButton: View
    private val nextButton: View

    init {
        inflate(context, R.layout.news_card_view, this)
        avatarView = findViewById(R.id.avatar)
        nameView = findViewById(R.id.name)
        dateView = findViewById(R.id.date)
        attachmentsView = findViewById(R.id.attachments_block)
        attachmentsViewPager = findViewById(R.id.attachments)
        textView = findViewById(R.id.text)
        likeBadge = findViewById(R.id.like_badge)
        skipBadge = findViewById(R.id.skip_badge)
        prevButton = findViewById(R.id.prev_button)
        nextButton = findViewById(R.id.next_button)
        nextButton.setOnClickListener { attachmentsViewPager.arrowScroll(View.FOCUS_RIGHT) }
        prevButton.setOnClickListener { attachmentsViewPager.arrowScroll(View.FOCUS_LEFT) }
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
        likeBadge.visibility = VISIBLE
    }

    fun hideLikeBadge() {
        likeBadge.visibility = GONE
    }

    fun showSkipBadge() {
        skipBadge.visibility = VISIBLE
    }

    fun hideSkipBadge() {
        skipBadge.visibility = GONE
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
        if (attachments.isEmpty()) {
            attachmentsViewPager.visibility = GONE
            attachmentsView.visibility = GONE
        } else {
            attachmentsViewPager.visibility = VISIBLE
            attachmentsView.visibility = VISIBLE
        }
        attachmentsViewPager.adapter = NewsAttachmentsViewPagerAdapter(attachments)
    }

    private fun showText(text: String) {
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
        textView.post {
            textView.invalidate()
        }
    }
}
