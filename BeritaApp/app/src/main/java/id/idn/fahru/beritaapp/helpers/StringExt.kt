package id.idn.fahru.beritaapp.helpers

import android.net.ParseException
import id.idn.fahru.beritaapp.App
import id.idn.fahru.beritaapp.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToLong

/**
 * Created by Imam Fahrur Rofi on 03/06/2020.
 */

enum class DateTypes {
    RelativeDate, LocalizedDate
}

fun String.toDate(dateTypes: DateTypes): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("GMT")
    val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm", Locale.getDefault())
    try {
        val date = inputFormat.parse(this)
        val diff: Long = System.currentTimeMillis() - (date?.time ?: 0)
        val hours = (diff / (60 * 60 * 1000).toFloat()).roundToLong()

        return if (hours < 24 && dateTypes != DateTypes.LocalizedDate) {
            val minuteAgo = App.context.getString(R.string.time_minute_ago)
            val minutesAgo = App.context.getString(R.string.time_minutes_ago)
            val hourAgo = App.context.getString(R.string.time_hour_ago)
            val hoursAgo = App.context.getString(R.string.time_hours_ago)
            val minutes = (diff / (60 * 1000).toFloat()).roundToLong()
            when {
                minutes < 2 -> "$minutes $minuteAgo"
                minutes < 60 -> "$minutes $minutesAgo"
                hours < 2 -> "$hours $hourAgo"
                else -> "$hours $hoursAgo"
            }
        } else {
            date?.let { outputFormat.format(it) } ?: ""
//                val days = (diff / (24.0 * 60 * 60 * 1000)).roundToLong()
//                when {
//                    days == 0L -> "today"
//                    days == 1L -> "yesterday"
//                    days < 14 -> "$days days ago"
//                    days < 30 -> (days / 7).toInt()
//                        .toString() + " weeks ago"
//                    days < 365 -> (days / 30).toInt()
//                        .toString() + " months ago"
//                    else -> (days / 365).toInt()
//                        .toString() + " years ago"
//                }
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return ""
}

fun String.crop(length: Int): String {
    return this.take(length) + if (this.length > length) "..." else ""
}
