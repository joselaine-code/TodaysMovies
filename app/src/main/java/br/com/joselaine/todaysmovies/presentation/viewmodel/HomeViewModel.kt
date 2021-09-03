package br.com.joselaine.todaysmovies.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.joselaine.todaysmovies.MoviePagingSource
import br.com.joselaine.todaysmovies.data.model.Movie
import br.com.joselaine.todaysmovies.domain.repository.HomeRepository
import br.com.joselaine.todaysmovies.domain.usecase.HomeUseCase
import br.com.joselaine.todaysmovies.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

class HomeViewModel(
    private val homeRepository: HomeRepository,
    private val homeUseCase: HomeUseCase
) : BaseViewModel() {

    private var mPagingData : Flow<PagingData<Movie>>? = null;

    fun getList(): Flow<PagingData<Movie>> {
        if(mPagingData != null) return mPagingData as Flow<PagingData<Movie>>
        else
            mPagingData = Pager(config = PagingConfig(pageSize = 20),
                pagingSourceFactory = { MoviePagingSource(homeRepository, homeUseCase) }).flow.cachedIn(
                viewModelScope)
        return mPagingData as Flow<PagingData<Movie>>
    }
}