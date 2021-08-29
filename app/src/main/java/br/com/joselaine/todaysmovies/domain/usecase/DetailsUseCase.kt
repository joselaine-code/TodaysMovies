package br.com.joselaine.todaysmovies.domain.usecase

import br.com.joselaine.todaysmovies.data.model.Movie
import br.com.joselaine.todaysmovies.domain.repository.DetailsRepository
import br.com.joselaine.todaysmovies.domain.repository.HomeRepository
import br.com.joselaine.todaysmovies.utils.ResponseApi
import br.com.joselaine.todaysmovies.utils.getFullImageUrl

class DetailsUseCase
constructor(
    private val repository : DetailsRepository
){
    suspend fun getMovieById(movieId: Int): ResponseApi {
        return when(val responseApi = repository.getMovieById(movieId)) {
            is ResponseApi.Success -> {
                val movie = responseApi.data as? Movie
                movie?.backdrop_path = movie?.backdrop_path?.getFullImageUrl().toString()
                ResponseApi.Success(movie)
            }
            is ResponseApi.Error -> {
                responseApi
            }
        }
    }

}
