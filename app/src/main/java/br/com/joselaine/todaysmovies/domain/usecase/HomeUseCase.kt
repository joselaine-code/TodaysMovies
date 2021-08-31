package br.com.joselaine.todaysmovies.domain.usecase

import br.com.joselaine.todaysmovies.data.model.Movie
import br.com.joselaine.todaysmovies.data.model.MovieResult
import br.com.joselaine.todaysmovies.domain.repository.HomeRepository
import br.com.joselaine.todaysmovies.utils.Constants.FIRST_PAGE
import br.com.joselaine.todaysmovies.utils.ResponseApi
import br.com.joselaine.todaysmovies.utils.dateFormat
import br.com.joselaine.todaysmovies.utils.getFullImageUrl

class HomeUseCase
constructor(
    private val repository : HomeRepository
) {
    suspend fun getPopularMovies(): ResponseApi {
        return when (val responseApi = repository.getPopularMovies(FIRST_PAGE)) {
            is ResponseApi.Success -> {
                val data = responseApi.data as? MovieResult
                val result = data?.results?.map {
                    it.backdrop_path = it.backdrop_path.getFullImageUrl()
                    it.poster_path = it.poster_path.getFullImageUrl()
                    it
                }
                ResponseApi.Success(result)
            }
            is ResponseApi.Error -> {
                responseApi
            }
        }
    }

    fun setupMoviesList(list: MovieResult?): List<Movie> {
        val movies = list?.results
        movies?.forEach { movie ->
            movie.poster_path = movie.poster_path?.getFullImageUrl()
            movie.backdrop_path = movie.backdrop_path?.getFullImageUrl()
            movie.release_date = movie.release_date?.dateFormat()
        }
        return movies ?: listOf()
    }



}