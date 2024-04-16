package com.example.movieapp.dependencyinjection

import com.example.movieapp.MainActivity
import com.example.movieapp.ui.details.MovieDetailsFragment
import com.example.movieapp.ui.search.SearchFragment
import com.example.movieapp.ui.home.HomeFragment
import com.example.movieapp.ui.favorites.FavoritesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class, DataModule::class, DomainModule::class, NetworkModule::class, ViewModelModule::class]
)
interface ApplicationComponent {
    fun inject(activity: MainActivity)
    fun inject(homeFragment: HomeFragment)
    fun inject(favoritesFragment: FavoritesFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(movieDetailsFragment: MovieDetailsFragment)


}