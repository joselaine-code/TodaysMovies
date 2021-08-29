package br.com.joselaine.todaysmovies.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.joselaine.todaysmovies.data.model.Movie
import br.com.joselaine.todaysmovies.domain.usecase.HomeUseCase
import br.com.joselaine.todaysmovies.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel constructor(private val homeUseCase : HomeUseCase)  : BaseViewModel() {

    private val _onSuccessPopular: MutableLiveData<List<Movie>> =
        MutableLiveData()
    val onSuccessPopular: LiveData<List<Movie>>
        get() = _onSuccessPopular

    fun getPopularMovies() {
        viewModelScope.launch {
            callApi(
                suspend { homeUseCase.getPopularMovies() },
                onSuccess = {
                    val result = it as? List<*>
                    _onSuccessPopular.postValue(
                       result?.filterIsInstance<Movie>()
                    )
                }
            )
        }
    }
}