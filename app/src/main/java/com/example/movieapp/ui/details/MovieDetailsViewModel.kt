package com.example.movieapp.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.MovieDetails
import com.example.movieapp.domain.movie.GetMoviesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails> = _movieDetails

    private val _showSnackBarError = MutableLiveData(false)
    val showSnackBarError: LiveData<Boolean> = _showSnackBarError

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            getMoviesUseCase(GetMoviesUseCase.Params(movieId))
                .onFailure {
                    _showSnackBarError.value = true
                }
                .onSuccess {
                    _movieDetails.value = it.movieDetails
                }
        }
    }
}