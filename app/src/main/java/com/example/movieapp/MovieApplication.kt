package com.example.movieapp

import android.app.Application
import com.example.movieapp.dependencyinjection.ApplicationComponent
import com.example.movieapp.dependencyinjection.ApplicationModule
import com.example.movieapp.dependencyinjection.DaggerApplicationComponent

class MovieApplication : Application() {
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()

    }
}

val Application.applicationComponent: ApplicationComponent
    get() {
        return (this as MovieApplication).appComponent
    }
