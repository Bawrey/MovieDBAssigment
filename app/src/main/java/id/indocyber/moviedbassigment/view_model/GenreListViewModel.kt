package id.indocyber.moviedbassigment.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.selection.SelectionTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import id.indocyber.api_service.use_case.GetGenreListUseCase
import id.indocyber.common.AppResponse
import id.indocyber.common.base.BaseViewModel
import id.indocyber.common.entity.genre.Genre
import id.indocyber.moviedbassigment.fragment.genre.GenreListFragmentDirections
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreListViewModel @Inject constructor(
    application: Application,
    private val getGenreListUseCase: GetGenreListUseCase
) : BaseViewModel(application) {

    val genreData = MutableLiveData<AppResponse<List<Genre>>>()
    var selection: SelectionTracker<Long>? = null

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            getGenreListUseCase.invoke().collect {
                genreData.postValue(it)
            }
        }
    }


    fun navigateToMovie() {
        navigate(
            GenreListFragmentDirections.toMovieList(
                selection?.selection?.toMutableList().orEmpty().joinToString(",")
            )
        )
    }
}