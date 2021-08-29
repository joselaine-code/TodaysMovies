package br.com.joselaine.todaysmovies.domain.repository

import br.com.joselaine.todaysmovies.data.retrofit.ApiService
import br.com.joselaine.todaysmovies.presentation.base.BaseRepository
import br.com.joselaine.todaysmovies.utils.ResponseApi

class DetailsRepository : BaseRepository() {
    suspend fun getMovieById(id: Int): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getMovieById(id)
        }
    }
}