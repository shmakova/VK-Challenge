package ru.shmakova.vk.presentation.card

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.shmakova.vk.R

class ContentCardView @JvmOverloads constructor(
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
        inflate(context, R.layout.content_card_view, this)
        avatarView = findViewById(R.id.avatar)
        nameView = findViewById(R.id.name)
        dateView = findViewById(R.id.date)
        photoView = findViewById(R.id.attachment)
        textView = findViewById(R.id.text)
    }

    fun setAvatar(url: String) {
        Glide.with(this)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(avatarView)
    }

    fun setName(name: String) {
        nameView.text = name
    }

    fun setDate(date: String) {
        dateView.text = date
    }

    fun setPhoto(url: String) {
        Glide.with(this)
            .load(url)
            .into(photoView)
    }

    fun setText(text: String) {
        textView.text = text
    }
}
