package com.example.movieapp.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.MovieApplication
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.databinding.FragmentSearchBinding
import com.example.movieapp.ui.home.adapter.MovieAdapter
import com.example.movieapp.ui.home.adapter.MovieClickListener
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class SearchFragment : Fragment(), MovieClickListener {

    private var _binding: FragmentSearchBinding? = null
    private var adapter: MovieAdapter = MovieAdapter(this)
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SearchViewModel by viewModels<SearchViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        (context.applicationContext as MovieApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        setupSearchView()
        observeEvents()
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvSearch.apply {
            layoutManager = GridLayoutManager(this@SearchFragment.context, 2)
            adapter = this@SearchFragment.adapter
        }
    }

    private fun observeEvents() {
        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.updateItems(it)
        }
        viewModel.showSnackBarError.observe(viewLifecycleOwner) {
            val view = this.view ?: return@observe
            if (it == true) {
                Snackbar.make(view, getString(R.string.something_went_wrong), Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.pb.visibility = if (it) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText ?: return true
                if (query.length > 1) {
                    viewModel.searchForMovies(query)
                }
                return true
            }
        })
    }

    override fun onCardClicked(movieId: Int) {
        val bundle = Bundle()
        bundle.putInt("movieId", movieId)
        findNavController().navigate(R.id.action_search_to_movie_details, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}