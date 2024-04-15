package com.example.movieapp.ui.home.tabs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.movieapp.ui.home.tabs.nowplaying.NowPlayingFragment
import com.example.movieapp.ui.home.tabs.popular.PopularFragment
import com.example.movieapp.ui.home.tabs.toprated.TopRatedFragment
import com.example.movieapp.ui.home.tabs.upcoming.UpcomingFragment

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragments = listOf(
        NowPlayingFragment(),
        PopularFragment(),
        TopRatedFragment(),
        UpcomingFragment()
    )

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Now Playing"
            1 -> "Popular"
            2 -> "Top Rated"
            4 -> "Up Coming"
            else -> ""
        }
    }
}