package com.example.movieapp.domain.movie

import com.example.movieapp.data.MovieRepository
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MovieDetails
import com.example.movieapp.domain.usecase.UseCase
import retrofit2.http.Query
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UseCase<SearchMoviesUseCase.Response, SearchMoviesUseCase.Params>() {
    override suspend fun get(params: Params?): Response {
        requireNotNull(params)
        val resultPage = movieRepository.searchMovie(params.query)
        return Response(resultPage.results)
    }

    data class Params(val query: String)
    data class Response(val movies: List<Movie>)
}