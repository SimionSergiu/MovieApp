package com.example.movieapp.ui.home.tabs.nowplaying

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.MovieApplication
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentFavoritesBinding
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.databinding.FragmentNowPlayingBinding
import com.example.movieapp.ui.home.delegates.MovieItemDelegate
import com.example.movieapp.ui.utils.DelegateAdapter
import com.example.movieapp.ui.utils.MainCompositeAdapter
import javax.inject.Inject

class NowPlayingFragment : Fragment() {

    private val binding get() = _binding!!
    private var _binding: FragmentNowPlayingBinding? = null

    private val adapter by lazy {
        MainCompositeAdapter.Builder()
            .add(MovieItemDelegate())
            .build()
    }

    override fun onAttach(context: Context) {
        (context.applicationContext as MovieApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: NowPlayingViewModel by viewModels<NowPlayingViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNowPlayingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.fetchNowPlayingMovies()
        setupAdapter()
        return root
    }

    private fun setupAdapter() {
        binding.rvNowPlaying.apply {
            adapter = this@NowPlayingFragment.adapter
            layoutManager = GridLayoutManager(this@NowPlayingFragment.context, 2)
        }
    }


}