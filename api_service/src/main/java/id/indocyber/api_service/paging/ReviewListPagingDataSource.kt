package id.indocyber.api_service.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.indocyber.api_service.service.MovieReviewService
import id.indocyber.common.entity.movie_review.Review

class ReviewListPagingDataSource(private val reviewService: MovieReviewService, private val movieId: String) :
    PagingSource<Int, Review>() {
    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        val page = params.key ?: 1
        val prevPage = if (page == 1) null else page - 1
        return try {
            val result = reviewService.getMovieReview(movie_id = movieId, page = page)
            return if (result.isSuccessful) {
                result.body()?.let {
                    val nextPage = if (it.reviews.isEmpty()) null else page + 1
                    LoadResult.Page(data = it.reviews, prevKey = prevPage, nextKey = nextPage)
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
            reviewService: MovieReviewService,
            pageSize: Int,
            movieId: String
        ): Pager<Int, Review> = Pager(config = PagingConfig(pageSize), pagingSourceFactory = {
            ReviewListPagingDataSource(reviewService, movieId)
        })
    }
}