package br.com.joselaine.todaysmovies.domain.repository

import br.com.joselaine.todaysmovies.data.retrofit.ApiService
import br.com.joselaine.todaysmovies.presentation.base.BaseRepository
import br.com.joselaine.todaysmovies.utils.ResponseApi

class SearchRepository : BaseRepository() {
    suspend fun searchMovie(query: String): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.searchMovie(query)
        }
    }
}