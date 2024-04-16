package com.example.movieapp.dependencyinjection

import com.example.movieapp.domain.MovieDbApiService
import com.example.movieapp.data.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {
    @Provides
    @Singleton
    fun provideMovieRepository(
        movieDbApiService: MovieDbApiService
    ): MovieRepository = MovieRepository(movieDbApiService)
}