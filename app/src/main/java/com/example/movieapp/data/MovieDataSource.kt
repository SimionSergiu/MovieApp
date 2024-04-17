package com.example.movieapp.data

import com.example.movieapp.domain.model.MovieDetails
import com.example.movieapp.domain.model.ResultPage
import com.example.movieapp.domain.movie.RecommendationType

interface MovieDataSource {
    suspend fun getMovieByRecommendationType(recommendationType: RecommendationType): ResultPage
    suspend fun searchMovie(query: String): ResultPage
    suspend fun getMovieDetails(movieId: Int): MovieDetails
}