package br.com.joselaine.todaysmovies

import androidx.paging.PageKeyedDataSource
import br.com.joselaine.todaysmovies.data.model.Movie
import br.com.joselaine.todaysmovies.data.model.MovieResult
import br.com.joselaine.todaysmovies.domain.repository.HomeRepository
import br.com.joselaine.todaysmovies.domain.usecase.HomeUseCase
import br.com.joselaine.todaysmovies.utils.Constants.FIRST_PAGE
import br.com.joselaine.todaysmovies.utils.ResponseApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class HomePageKeyedDataSource(
    private val homeRepository: HomeRepository,
    private val homeUseCase: HomeUseCase
) : PageKeyedDataSource<Int, Movie>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        CoroutineScope(IO).launch {
            val movies: List<Movie> = callPopularMoviesApi(FIRST_PAGE)
            callback.onResult(movies, null, FIRST_PAGE + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        loadData(params.key, params.key - 1, callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        loadData(params.key, params.key + 1, callback)
    }

    private fun loadData(page: Int, nextPage: Int, callback: LoadCallback<Int, Movie>) {
        CoroutineScope(IO).launch {
            val movies: List<Movie> = callPopularMoviesApi(page)
            callback.onResult(movies, nextPage)
        }
    }

    private suspend fun callPopularMoviesApi(page: Int): List<Movie> {
        return when (
            val response = homeRepository.getPopularMovies(page)
        ) {
            is ResponseApi.Success -> {
                val list = response.data as? MovieResult
                homeUseCase.setupMoviesList(list)
            }
            is ResponseApi.Error -> {
                listOf()
            }
        }
    }
}