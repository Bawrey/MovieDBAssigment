package id.indocyber.moviedbassigment.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.indocyber.api_service.use_case.GetMovieDetailUseCase
import id.indocyber.api_service.use_case.GetMovieReviewPagingUseCase
import id.indocyber.common.AppResponse
import id.indocyber.common.base.BaseViewModel
import id.indocyber.common.entity.movie_detail.MovieDetailResponse
import id.indocyber.common.entity.movie_review.Review
import id.indocyber.moviedbassigment.fragment.detail.DetailFragmentDirections
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    application: Application,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieReviewPagingUseCase: GetMovieReviewPagingUseCase
) : BaseViewModel(application) {

    val detailData = MutableLiveData<AppResponse<MovieDetailResponse>>()
    var reviewData = MutableLiveData<PagingData<Review>>()

    fun loadData(movieId: String) {
        viewModelScope.launch {
            getMovieDetailUseCase.invoke(movieId).collect {
                detailData.postValue(it)
            }
        }

    }

    fun loadSecondData(movieId: String) {
        if (reviewData.value == null) {
            viewModelScope.launch {
                getMovieReviewPagingUseCase.invoke(movieId).flow.cachedIn(this).collect {
                    reviewData.postValue(it)
                }
            }
        } else {
            reviewData.postValue(reviewData.value)
        }
    }

    fun navigateToVideo(movieId: String, posterPath: String) {
        navigate(
            DetailFragmentDirections.toVideo(movieId, posterPath)
        )
    }
}