package id.indocyber.api_service.use_case

import id.indocyber.api_service.service.MovieDetailService
import id.indocyber.common.AppResponse
import id.indocyber.common.entity.movie_detail.MovieDetailResponse
import kotlinx.coroutines.flow.flow

class GetMovieDetailUseCase(private val movieDetailService: MovieDetailService) {
    operator fun invoke(movieId: String) = flow<AppResponse<MovieDetailResponse>> {
        emit(AppResponse.loading())
        val result = movieDetailService.getMovieDetail(movieId)
        if (result.isSuccessful) {
            emit(result.body()?.let {
                AppResponse.success(it)
            } ?: kotlin.run {
                AppResponse.failure(Exception("Data is empty: " + result.message()))
            })
        } else {
            emit(AppResponse.failure(Exception("Request is not successful: " + result.message())))
        }
    }
}