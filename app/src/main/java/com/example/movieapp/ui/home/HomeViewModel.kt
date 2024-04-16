package com.example.movieapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.MovieDetails
import com.example.movieapp.domain.model.toMovieItem
import com.example.movieapp.domain.movie.GetMovieByTypeUseCase
import com.example.movieapp.domain.movie.RecommendationType
import com.example.movieapp.ui.home.items.MovieItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getMovieByTypeUseCase: GetMovieByTypeUseCase
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _showSnackBarError = MutableLiveData<Boolean>(false)
    val showSnackBarError: LiveData<Boolean> = _showSnackBarError

    private val _moviesItems = MutableLiveData<List<MovieItem>>(mutableListOf())
    val moviesItems: LiveData<List<MovieItem>> = _moviesItems

    private val _sortedMoviesItems = MutableLiveData<List<MovieItem>>(mutableListOf())
    val sortedMoviesItems: LiveData<List<MovieItem>> = _sortedMoviesItems

    fun updateItemsForTab(position: Int?) {
        viewModelScope.launch {
            _isLoading.value = true
            val recommendationType = recommendationType(position)
            val params = GetMovieByTypeUseCase.Params(recommendationType)
            getMovieByTypeUseCase(params)
                .onFailure {
                    _isLoading.value = false
                    _showSnackBarError.value = true
                }
                .onSuccess { response ->
                    _isLoading.value = false
                    _moviesItems.value =  response.movies.map { it.toMovieItem() }
                }
        }

    }

    private fun recommendationType(position: Int?): RecommendationType {
        val recommendationType = when (position) {
            0 -> RecommendationType.NOW_PLAYING
            1 -> RecommendationType.POPULAR
            2 -> RecommendationType.TOP_RATED
            3 -> RecommendationType.UPCOMING
            else -> RecommendationType.NOW_PLAYING //  first tab?
        }
        return recommendationType
    }

    fun sortMoviesRatingAscending() {
        val items = _moviesItems.value ?: emptyList()
        _sortedMoviesItems.value = items.sortedBy { it.voteAverage }
    }

    fun sortMoviesRatingDescending() {
        val items = _moviesItems.value ?: emptyList()
        _sortedMoviesItems.value = items.sortedByDescending { it.voteAverage }
    }

    fun sortMoviesReleaseDateAscending() {
        val items = _moviesItems.value ?: emptyList()
        _sortedMoviesItems.value = items.sortedBy { it.dateCalendar?.time }
    }

    fun sortMoviesReleaseDateDescending() {
        val items = _moviesItems.value ?: emptyList()
        _sortedMoviesItems.value = items.sortedByDescending { it.dateCalendar?.time }
    }
}