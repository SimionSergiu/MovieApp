package com.example.movieapp.dependencyinjection

import android.app.Application
import com.example.movieapp.MovieApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: MovieApplication){
    @Provides
    @Singleton
    fun provideApplication(): Application = application
}