package br.com.joselaine.todaysmovies.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.joselaine.todaysmovies.data.model.Movie
import br.com.joselaine.todaysmovies.domain.usecase.DetailsUseCase
import br.com.joselaine.todaysmovies.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class DetailsViewModel
constructor(private val detailsUseCase: DetailsUseCase) : BaseViewModel() {

    private val _onSuccessMovieById: MutableLiveData<Movie> = MutableLiveData()
    val onSuccessMovieById: LiveData<Movie>
        get() = _onSuccessMovieById


    fun getMovieById(movieId: Int) {
        viewModelScope.launch {
            callApi(
                suspend { detailsUseCase.getMovieById(movieId) },
                onSuccess = {
                    _onSuccessMovieById.postValue(it as? Movie)
                }
            )
        }
    }
}