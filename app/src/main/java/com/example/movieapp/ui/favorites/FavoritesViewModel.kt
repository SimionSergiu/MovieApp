package com.example.movieapp.ui.favorites

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.movie.GetMoviesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            getMoviesUseCase(GetMoviesUseCase.Params(movieId))
                .onFailure { }
                .onSuccess {
                    it.movieDetails
                    Log.d("###", "${it.movieDetails}")
                }
        }
    }
}