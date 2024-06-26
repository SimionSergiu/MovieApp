package com.example.movieapp.domain.movie

import com.example.movieapp.data.MovieRepository
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.usecase.UseCase
import javax.inject.Inject

class GetMovieByTypeUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : UseCase<GetMovieByTypeUseCase.Response, GetMovieByTypeUseCase.Params>() {
    override suspend fun get(params: Params?): Response {
        requireNotNull(params)
        val resultPage = movieRepository.getMovieByRecommendationType(params.recommendationType)
        return Response(resultPage.results)
    }

    data class Params(val recommendationType: RecommendationType)
    data class Response(val movies: List<Movie>)
}