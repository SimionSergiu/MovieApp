package com.example.movieapp.dependencyinjection

import com.example.movieapp.MainActivity
import com.example.movieapp.ui.search.SearchFragment
import com.example.movieapp.ui.home.HomeFragment
import com.example.movieapp.ui.favorites.FavoritesFragment
import com.example.movieapp.ui.home.tabs.nowplaying.NowPlayingFragment
import com.example.movieapp.ui.home.tabs.popular.PopularFragment
import com.example.movieapp.ui.home.tabs.toprated.TopRatedFragment
import com.example.movieapp.ui.home.tabs.upcoming.UpcomingFragment
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
    fun inject(searchFragment: NowPlayingFragment)
    fun inject(searchFragment: PopularFragment)
    fun inject(searchFragment: TopRatedFragment)
    fun inject(searchFragment: UpcomingFragment)


}