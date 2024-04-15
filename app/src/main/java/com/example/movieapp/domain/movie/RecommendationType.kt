package com.example.movieapp.domain.movie

enum class RecommendationType(val string: String) {
    NOW_PLAYING("now_playing"),
    POPULAR("popular"),
    TOP_RATED("top_rated"),
    UPCOMING("upcoming")
}