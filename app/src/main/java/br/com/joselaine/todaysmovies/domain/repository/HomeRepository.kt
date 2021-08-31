package br.com.joselaine.todaysmovies.domain.repository

import br.com.joselaine.todaysmovies.data.retrofit.ApiService
import br.com.joselaine.todaysmovies.presentation.base.BaseRepository
import br.com.joselaine.todaysmovies.utils.ResponseApi

class HomeRepository : BaseRepository() {
    suspend fun getPopularMovies(page: Int): ResponseApi {
        return safeApiCall {
            ApiService.tmdbApi.getPopularMovies(page)
        }
    }
}