package com.austinevick.noteapp.core

import android.graphics.Color.parseColor
import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.Date

fun convertLongToDate(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("dd/MMM/yy HH:mma")
    return format.format(date)
}


val String.color
    get() = Color(parseColor(this))