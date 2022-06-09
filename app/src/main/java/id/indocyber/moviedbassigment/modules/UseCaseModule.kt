package id.indocyber.moviedbassigment.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import id.indocyber.api_service.service.*
import id.indocyber.api_service.use_case.*

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetGenreListUseCase(genreListService: GenreListService) =
        GetGenreListUseCase(genreListService)

    @Provides
    fun provideGetMovieDetailUseCase(movieDetailService: MovieDetailService) =
        GetMovieDetailUseCase(movieDetailService)

    @Provides
    fun getMovieListPagingUseCase(movieListService: MovieListService) =
        GetMovieListPagingUseCase(movieListService)

    @Provides
    fun getMovieReviewPagingUseCase(movieReviewService: MovieReviewService) =
        GetMovieReviewPagingUseCase(movieReviewService)

    @Provides
    fun getMovieVideoUseCase(movieVideoService: MovieVideoService) =
        GetMovieVideoUseCase(movieVideoService)
}