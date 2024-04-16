package com.example.movieapp.domain.movie

import com.example.movieapp.data.MovieRepository
import com.example.movieapp.domain.model.MovieDetails
import com.example.movieapp.domain.usecase.UseCase
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UseCase<GetMoviesUseCase.Response, GetMoviesUseCase.Params>() {
    override suspend fun get(params: Params?): Response {
        requireNotNull(params)
        val movieDetails = movieRepository.getMovieDetails(movieId = params.id)
        return Response(movieDetails)
    }

    data class Params(val id: Int)
    data class Response(val movieDetails: MovieDetails)
}