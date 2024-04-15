package com.example.movieapp.domain.movie

import com.example.movieapp.domain.model.MovieDetails
import com.example.movieapp.domain.model.Movie

interface MovieDataSource {
    suspend fun getMovieByRecommendationType(recommendationType: RecommendationType): List<Movie>
    suspend fun searchMovie(): List<Movie>
    suspend fun getMovieDetails(movieId: Int): MovieDetails
}