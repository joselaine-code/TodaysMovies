package br.com.joselaine.todaysmovies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.joselaine.todaysmovies.data.model.Movie
import br.com.joselaine.todaysmovies.data.model.MovieResult
import br.com.joselaine.todaysmovies.domain.repository.HomeRepository
import br.com.joselaine.todaysmovies.domain.usecase.HomeUseCase
import br.com.joselaine.todaysmovies.utils.ResponseApi

class MoviePagingSource(
    private val homeRepository: HomeRepository,
    private val homeUseCase: HomeUseCase
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        return try {
            val page: Int = params.key ?: 1
            val response = callPopularMoviesApi(page)

            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else -1,
                nextKey = page.plus(1)
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
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