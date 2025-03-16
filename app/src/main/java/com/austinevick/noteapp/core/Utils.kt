package com.austinevick.noteapp.core

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun convertLongToDate(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("dd/MMM/yy HH:mma",Locale.getDefault())
    return format.format(date)
}


val String.color
    get() = Color(this.toColorInt())