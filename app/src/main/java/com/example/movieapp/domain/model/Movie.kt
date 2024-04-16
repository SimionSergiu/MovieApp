package com.example.movieapp.domain.model

import com.example.movieapp.ui.home.items.MovieItem
import com.example.movieapp.ui.utils.getCalendar
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("adult") val isForAdults: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("id") val id: Int,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("title") val title: String,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
)
fun Movie.toMovieItem() : MovieItem {
    val dateCalendar = getCalendar(this.releaseDate)

    return MovieItem(
        id = this.id,
        title = this.title,
        dateCalendar = dateCalendar,
        backdropPath = this.backdropPath,
        voteAverage = this.voteAverage,
        posterPath = this. posterPath,
    )
}