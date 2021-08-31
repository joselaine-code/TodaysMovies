package br.com.joselaine.todaysmovies.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import br.com.joselaine.todaysmovies.HomeDataSourceFactory
import br.com.joselaine.todaysmovies.data.model.Movie
import br.com.joselaine.todaysmovies.domain.usecase.HomeUseCase
import br.com.joselaine.todaysmovies.presentation.base.BaseViewModel
import br.com.joselaine.todaysmovies.utils.Constants.PAGE_SIZE
import kotlinx.coroutines.launch

class HomeViewModel(
    homeDataSourceFactory: HomeDataSourceFactory,
    private val homeUseCase: HomeUseCase
) : BaseViewModel() {

    var moviesPagedList: LiveData<PagedList<Movie>>? = null
    private var watchMoviesLiveDataSource: LiveData<PageKeyedDataSource<Int, Movie>>? = null

    init {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE).build()

        watchMoviesLiveDataSource = homeDataSourceFactory.getLiveDataSource()
        moviesPagedList = LivePagedListBuilder(homeDataSourceFactory, pagedListConfig)
            .build()
    }
}