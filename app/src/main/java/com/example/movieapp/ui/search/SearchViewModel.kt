package com.example.movieapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.toMovieItem
import com.example.movieapp.domain.movie.SearchMoviesUseCase
import com.example.movieapp.ui.home.items.MovieItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {


    private val _movies = MutableLiveData<List<MovieItem>>()
    val movies: LiveData<List<MovieItem>> = _movies

    private val _showSnackBarError = MutableLiveData<Boolean>(false)
    val showSnackBarError: LiveData<Boolean> = _showSnackBarError

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchForMovies(query: String) {
        _isLoading.value = true
        viewModelScope.launch {
            searchMoviesUseCase(SearchMoviesUseCase.Params(query))
                .onFailure {
                    _showSnackBarError.value = true
                    _isLoading.value = false
                }
                .onSuccess { response ->
                    _movies.value = response.movies.map { it.toMovieItem() }
                    _isLoading.value = false
                }
        }
    }
}