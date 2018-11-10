package ru.shmakova.vk.presentation.utils

import android.content.Context
import android.text.format.DateUtils
import android.text.format.DateUtils.FORMAT_ABBREV_MONTH
import android.text.format.DateUtils.FORMAT_SHOW_DATE
import java.util.*

object FormatUtils {
    fun getTimeFromDate(context: Context, date: Date): String {
        return DateUtils.formatDateTime(
            context,
            date.time,
            DateUtils.FORMAT_SHOW_TIME
        )
    }

    fun getRelativeDayFromDate(date: Date): String {
        val flags = (DateUtils.FORMAT_ABBREV_WEEKDAY or FORMAT_SHOW_DATE or FORMAT_ABBREV_MONTH)
        return DateUtils.getRelativeTimeSpanString(
            date.time,
            System.currentTimeMillis(),
            DateUtils.DAY_IN_MILLIS,
            flags
        ).toString().toLowerCase(Locale.ENGLISH)
    }
}
