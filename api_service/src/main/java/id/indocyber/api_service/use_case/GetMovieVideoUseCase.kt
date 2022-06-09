package id.indocyber.api_service.use_case

import id.indocyber.api_service.service.MovieVideoService
import id.indocyber.common.AppResponse
import id.indocyber.common.entity.movie_video.Video
import kotlinx.coroutines.flow.flow

class GetMovieVideoUseCase(private val movieVideoService: MovieVideoService) {
    operator fun invoke(movieId: String) = flow<AppResponse<Video>> {
        emit(AppResponse.loading())
        val result = movieVideoService.getMovieVideo(movieId)
        if (result.isSuccessful) {
            emit(result.body()?.videos?.let { list ->
                if (list.isEmpty()) AppResponse.failure(Exception("Data is empty: " + "list is empty"))
                else AppResponse.success(
                    list.filter {
                        it.type == "Trailer"
                    }[0]
                )
            } ?: kotlin.run {
                AppResponse.failure(Exception("Data is empty: " + result.message()))
            })
        } else {
            emit(AppResponse.failure(Exception("Request is not successful: " + result.message())))
        }
    }
}