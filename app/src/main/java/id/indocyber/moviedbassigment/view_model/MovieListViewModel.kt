package id.indocyber.moviedbassigment.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.indocyber.api_service.use_case.GetMovieListPagingUseCase
import id.indocyber.common.base.BaseViewModel
import id.indocyber.common.entity.genre.Genre
import id.indocyber.common.entity.movie.Movie
import id.indocyber.moviedbassigment.fragment.movie.MovieListFragmentDirections
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    application: Application,
    private val getMovieListPagingUseCase: GetMovieListPagingUseCase
) : BaseViewModel(application) {

    val movieData = MutableLiveData<PagingData<Movie>>()

    fun loadData(genreIds: String) {
        viewModelScope.launch {
            getMovieListPagingUseCase.invoke(genreIds).flow.collect {
                movieData.postValue(it)
            }
        }
    }

    fun navigateToDetails(movieId: String) {
        navigate(
            MovieListFragmentDirections.toDetails(movieId)
        )
    }
}