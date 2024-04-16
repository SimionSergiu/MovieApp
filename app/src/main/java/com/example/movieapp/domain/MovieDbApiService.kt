package com.example.movieapp.domain

import com.example.movieapp.domain.model.MovieDetails
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.ResultPage
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDbApiService {
    @GET("movie/{recommendation_type}")
    suspend fun getMovieByType(
        @Path("recommendation_type") recommendationType: String,
        @Query("api_key") api_key: String
    ): ResultPage

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") api_key: String
    ): ResultPage

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String
    ): MovieDetails

    companion object {
        fun createApiService(): MovieDbApiService {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(Constants.MOVIE_DB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService: MovieDbApiService = retrofit.create(MovieDbApiService::class.java)
            return apiService
        }
    }
}

