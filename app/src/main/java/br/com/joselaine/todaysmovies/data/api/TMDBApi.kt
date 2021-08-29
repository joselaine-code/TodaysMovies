package br.com.joselaine.todaysmovies.data.api

import android.app.SearchManager.QUERY
import br.com.joselaine.todaysmovies.data.model.Movie
import br.com.joselaine.todaysmovies.data.model.MovieResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<MovieResult>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query(QUERY) query: String
    ): Response<MovieResult>

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId: Int
    ): Response<Movie>
}
