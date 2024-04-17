package com.example.movieapp.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.MovieApplication
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.ui.details.MovieDetailsFragment
import com.example.movieapp.ui.home.adapter.MovieAdapter
import com.example.movieapp.ui.home.adapter.MovieClickListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import javax.inject.Inject

class HomeFragment : Fragment(), TabLayout.OnTabSelectedListener, MovieClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: HomeViewModel by viewModels<HomeViewModel> { viewModelFactory }
    private var adapter: MovieAdapter = MovieAdapter(this)
    override fun onAttach(context: Context) {
        (context.applicationContext as MovieApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.movie_filters, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.rating_ascending -> viewModel.sortMoviesRatingAscending()
            R.id.rating_descending -> viewModel.sortMoviesRatingDescending()
            R.id.release_date_ascending -> viewModel.sortMoviesReleaseDateAscending()
            R.id.release_date_descending -> viewModel.sortMoviesReleaseDateDescending()
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupTabLayout()
        setupRecyclerView()
        observeEvents()
        viewModel.updateItemsForTab(0)
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@HomeFragment.context, 2)
            adapter = this@HomeFragment.adapter
        }
    }

    private fun observeEvents() {
        viewModel.moviesItems.observe(viewLifecycleOwner) {
            adapter.updateItems(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        viewModel.showSnackBarError.observe(viewLifecycleOwner) {
            val view = this.view ?: return@observe
            if (it == true) {
                Snackbar.make(view, getString(R.string.something_went_wrong), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setupTabLayout() {
        val nowPlaying = getString(R.string.home_tab_now_playing)
        val popular = getString(R.string.home_tab_popular)
        val topRated = getString(R.string.home_tab_top_rated)
        val upcoming = getString(R.string.home_tab_upcoming)

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(nowPlaying))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(popular))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(topRated))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(upcoming))

        binding.tabLayout.addOnTabSelectedListener(this)
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        viewModel.updateItemsForTab(tab?.position)
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onCardClicked(movieId: Int) {
        val bundle = Bundle()
        bundle.putInt("movieId", movieId)
        findNavController().navigate(R.id.action_home_to_movie_details, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}