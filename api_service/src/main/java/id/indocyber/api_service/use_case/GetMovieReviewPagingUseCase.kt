package id.indocyber.api_service.use_case

import id.indocyber.api_service.paging.ReviewListPagingDataSource
import id.indocyber.api_service.service.MovieReviewService

class GetMovieReviewPagingUseCase(private val reviewService: MovieReviewService) {
    operator fun invoke(movieId: String) =
        ReviewListPagingDataSource.createPager(reviewService, 10, movieId)
}