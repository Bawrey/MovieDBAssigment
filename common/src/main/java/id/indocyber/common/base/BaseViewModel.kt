package id.indocyber.common.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavDirections

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val navigationEvent = SingleLiveEvent<NavDirections>()
    val popBackStackEvent = SingleLiveEvent<Any>()

    fun navigate(nav: NavDirections) {
        navigationEvent.postValue(nav)
    }

    fun popBackStack() {
        popBackStackEvent.postValue(Any())
    }

}