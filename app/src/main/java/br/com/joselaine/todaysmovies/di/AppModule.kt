package br.com.joselaine.todaysmovies.di

import br.com.joselaine.todaysmovies.MoviePagingSource
import br.com.joselaine.todaysmovies.domain.repository.DetailsRepository
import br.com.joselaine.todaysmovies.domain.repository.HomeRepository
import br.com.joselaine.todaysmovies.domain.repository.SearchRepository
import br.com.joselaine.todaysmovies.domain.usecase.DetailsUseCase
import br.com.joselaine.todaysmovies.domain.usecase.HomeUseCase
import br.com.joselaine.todaysmovies.domain.usecase.SearchUseCase
import br.com.joselaine.todaysmovies.presentation.viewmodel.DetailsViewModel
import br.com.joselaine.todaysmovies.presentation.viewmodel.HomeViewModel
import br.com.joselaine.todaysmovies.presentation.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    val appModule = module {
        single { HomeRepository() }
        single { SearchRepository() }
        factory { DetailsRepository() }

        single { DetailsUseCase(repository = get()) }
        single { HomeUseCase(repository = get()) }
        single { SearchUseCase(repository = get()) }

        single {
            MoviePagingSource(
                homeRepository = get(),
                homeUseCase = get()
            )
        }

        viewModel { DetailsViewModel(detailsUseCase = get()) }
        viewModel { HomeViewModel(homeUseCase = get(), homeRepository = get()) }
        viewModel { SearchViewModel(searchUseCase = get()) }
    }
}
