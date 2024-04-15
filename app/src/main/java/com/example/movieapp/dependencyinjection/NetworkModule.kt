package com.example.movieapp.dependencyinjection

import com.example.movieapp.domain.MovieDbApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideMovieDbApiService(): MovieDbApiService = MovieDbApiService.createApiService()
}