package id.indocyber.api_service.service

import id.indocyber.api_service.Constants
import id.indocyber.common.entity.genre.GenreListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreListService {
    @GET("genre/movie/list")
    suspend fun getGenreList(
        @Query("api_key") api_key: String = Constants.API_KEY_MOVIE
    ): Response<GenreListResponse>
}