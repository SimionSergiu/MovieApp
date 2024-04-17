package com.example.movieapp.ui.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun getCalendar(dateString: String): Calendar? {
    if (dateString.isBlank()) return null
    val calendar = Calendar.getInstance()
    calendar.time = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateString) ?: Date()
    return calendar
}