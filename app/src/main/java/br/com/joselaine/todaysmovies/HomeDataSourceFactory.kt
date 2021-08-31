package br.com.joselaine.todaysmovies

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import br.com.joselaine.todaysmovies.data.model.Movie

class HomeDataSourceFactory(
    private val tmdbDataSource: HomePageKeyedDataSource
): DataSource.Factory<Int, Movie>() {

    private val tmdbLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, Movie>>()
    override fun create(): DataSource<Int, Movie> {
        tmdbLiveDataSource.postValue(tmdbDataSource)
        return tmdbDataSource
    }

    fun getLiveDataSource() : MutableLiveData<PageKeyedDataSource<Int, Movie>> {
        return tmdbLiveDataSource
    }
}