package id.indocyber.api_service.service

import id.indocyber.api_service.Constants
import id.indocyber.common.entity.movie.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieListService {
    @GET("discover/movie")
    suspend fun getMovieList(
        @Query("api_key") api_key: String = Constants.API_KEY_MOVIE,
        @Query("with_genres") genreId: String,
        @Query("page") page: Int
    ): Response<MovieListResponse>
}