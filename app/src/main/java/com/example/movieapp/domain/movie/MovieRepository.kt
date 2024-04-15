package com.example.movieapp.domain.movie

import com.example.movieapp.domain.Constants
import com.example.movieapp.domain.MovieDbApiService
import com.example.movieapp.domain.model.MovieDetails
import com.example.movieapp.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: MovieDbApiService
) : MovieDataSource {
    override suspend fun getMovieByRecommendationType(recommendationType: RecommendationType): List<Movie> = withContext(Dispatchers.IO) {
            apiService.getMovieByType(recommendationType.name, Constants.MOVIE_DB_API_KEY)
        }

    override suspend fun searchMovie(): List<Movie> = withContext(Dispatchers.IO) {
        apiService.searchMovies("query", Constants.MOVIE_DB_API_KEY)
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetails = withContext(Dispatchers.IO) {
        apiService.getMovieDetails(movieId, Constants.MOVIE_DB_API_KEY)
    }

}