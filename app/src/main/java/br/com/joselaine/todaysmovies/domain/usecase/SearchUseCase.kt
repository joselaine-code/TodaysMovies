package br.com.joselaine.todaysmovies.domain.usecase

import br.com.joselaine.todaysmovies.data.model.MovieResult
import br.com.joselaine.todaysmovies.domain.repository.HomeRepository
import br.com.joselaine.todaysmovies.domain.repository.SearchRepository
import br.com.joselaine.todaysmovies.utils.ResponseApi
import br.com.joselaine.todaysmovies.utils.getFullImageUrl

class SearchUseCase
constructor(
    private val repository : SearchRepository
){
    suspend fun searchMovie(query: String): ResponseApi {
        return when (val responseApi = repository.searchMovie(query)) {
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
}