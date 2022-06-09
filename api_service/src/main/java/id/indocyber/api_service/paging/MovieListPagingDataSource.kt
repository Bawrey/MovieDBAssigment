package id.indocyber.api_service.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.indocyber.api_service.service.MovieListService
import id.indocyber.common.entity.genre.Genre
import id.indocyber.common.entity.movie.Movie

class MovieListPagingDataSource(
    private val movieListService: MovieListService,
    private val genres: String
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        val prevPage = if (page == 1) null else page - 1
        return try {
            val result = movieListService.getMovieList(genreId = genres, page = page)
            return if (result.isSuccessful) {
                result.body()?.let {
                    val nextPage = if (it.movies.isEmpty()) null else page + 1
                    LoadResult.Page(data = it.movies, prevKey = prevPage, nextKey = nextPage)
                } ?: kotlin.run {
                    LoadResult.Error(Exception("Data is empty"))
                }
            } else {
                LoadResult.Error(Exception("Request is not successful"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        fun createPager(
            movieListService: MovieListService,
            pageSize: Int,
            genres: String
        ): Pager<Int, Movie> = Pager(config = PagingConfig((pageSize)), pagingSourceFactory = {
            MovieListPagingDataSource(movieListService, genres)
        })
    }
}