package id.indocyber.moviedbassigment.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.indocyber.api_service.use_case.GetMovieVideoUseCase
import id.indocyber.common.AppResponse
import id.indocyber.common.base.BaseViewModel
import id.indocyber.common.entity.movie_video.Video
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    application: Application,
    val getMovieVideoUseCase: GetMovieVideoUseCase
) : BaseViewModel(application) {

    val videoData = MutableLiveData<AppResponse<Video>>()

    fun loadData(movieId:String){
        viewModelScope.launch {
            getMovieVideoUseCase.invoke(movieId).collect{
                videoData.postValue(it)
            }
        }
    }
}