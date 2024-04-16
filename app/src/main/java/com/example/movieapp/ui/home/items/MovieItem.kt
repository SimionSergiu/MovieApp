package com.example.movieapp.ui.home.items

import java.util.Calendar

data class MovieItem(
    val id: Int,
    val title: String,
    val dateCalendar: Calendar?,
    val backdropPath: String?,
    val voteAverage: Double,
    val posterPath: String?
)