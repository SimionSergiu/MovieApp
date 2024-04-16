package com.example.movieapp.ui.details

import android.content.Context
import android.graphics.drawable.Drawable
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.movieapp.MovieApplication
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.databinding.FragmentMovieDetailsBinding
import com.example.movieapp.domain.Constants
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MovieDetails
import com.example.movieapp.ui.home.HomeViewModel
import com.example.movieapp.ui.home.adapter.MovieAdapter
import com.example.movieapp.ui.utils.getCalendar
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import javax.inject.Inject

class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MovieDetailsViewModel by viewModels<MovieDetailsViewModel> { viewModelFactory }
    override fun onAttach(context: Context) {
        (context.applicationContext as MovieApplication).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Hide the activity's toolbar
        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        val argValue = arguments?.getInt("movieId")
        if (argValue != null) {
            viewModel.fetchMovieDetails(movieId = argValue)
        } else {
            Throwable("Should not be null at this point")
        }
        observeEvents()
        setupListeners()
        return binding.root
    }

    private fun setupListeners() {
        binding.ivClose.setOnClickListener {
            this.findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.show()
    }

    private fun observeEvents() {
        viewModel.movieDetails.observe(viewLifecycleOwner) { movieDetails ->
            binding.apply {
                setTextViews(movieDetails)
                binding.chipGroup.apply {
                    val generes = if (movieDetails.genres.size >= 2) {
                        movieDetails.genres.subList(0, 2)
                    } else {
                        movieDetails.genres
                    }
                    generes.forEach {
                        val chip = Chip(context)
                        chip.text = it.name
                        chip.isClickable = false
                        chip.isCloseIconVisible = false
                        chipGroup.addView(chip)
                    }
                }
                loadImageWithGlide(movieDetails.backdropPath, ivBackdrop)
                loadImageWithGlide(movieDetails.posterPath, ivPoster)
            }

        }

        viewModel.showSnackBarError.observe(viewLifecycleOwner) {
            val view = this.view ?: return@observe
            if (it == true) {
                Snackbar.make(view, getString(R.string.something_went_wrong), Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun FragmentMovieDetailsBinding.setTextViews(movieDetails: MovieDetails) {
        val year = getCalendar(movieDetails.releaseDate)?.get(Calendar.YEAR)?.toString() ?: ""
        val rating = movieDetails.voteAverage.toString().substring(0, 3)
        tvOverviewDescription.text = movieDetails.overview
        tvTitle.text = movieDetails.title
        tvReleaseDate.text = getString(R.string.released_in, year)
        tvRating.text = getString(R.string.rating, rating)
        tvTagLine.apply {
            if (movieDetails.tagline.isBlank()) {
                visibility = View.GONE
            }
            text = movieDetails.tagline
        }
        tvVoteCount.text = "(${movieDetails.voteCount})"
    }

    private fun loadImageWithGlide(path: String?, view: ImageView) {
        val imageUrl = Constants.MOVIE_DB_POSTER_BASE_URL + path
        Glide.with(binding.root.context)
            .load(imageUrl)
            .error(R.drawable.image_sad_face)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }

}
