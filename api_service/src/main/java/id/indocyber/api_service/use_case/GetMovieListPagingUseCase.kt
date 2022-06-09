package id.indocyber.api_service.use_case

import id.indocyber.api_service.paging.MovieListPagingDataSource
import id.indocyber.api_service.service.MovieListService
import id.indocyber.common.entity.genre.Genre

class GetMovieListPagingUseCase(private val movieListService: MovieListService) {
    operator fun invoke(list: String) =
        MovieListPagingDataSource.createPager(movieListService, 10, list)
}