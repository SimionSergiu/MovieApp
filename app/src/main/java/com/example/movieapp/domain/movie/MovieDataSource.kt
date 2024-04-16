package com.example.movieapp.domain.movie

import com.example.movieapp.domain.model.MovieDetails
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.ResultPage
import retrofit2.http.Query

interface MovieDataSource {
    suspend fun getMovieByRecommendationType(recommendationType: RecommendationType): ResultPage
    suspend fun searchMovie(query: String): ResultPage
    suspend fun getMovieDetails(movieId: Int): MovieDetails
}