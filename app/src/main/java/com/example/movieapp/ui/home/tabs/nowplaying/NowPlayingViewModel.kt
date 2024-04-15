package com.example.movieapp.ui.home.tabs.nowplaying

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.movie.GetMovieByTypeUseCase
import com.example.movieapp.domain.movie.RecommendationType
import kotlinx.coroutines.launch
import javax.inject.Inject

class NowPlayingViewModel @Inject constructor(
    private val getMovieByTypeUseCase: GetMovieByTypeUseCase
): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun fetchNowPlayingMovies(){
        viewModelScope.launch {
            getMovieByTypeUseCase(GetMovieByTypeUseCase.Params(RecommendationType.NOW_PLAYING))
                .onFailure {  }
                .onSuccess {

                }
        }
    }
}