package lv.zesloka.skani.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lv.zesloka.domain.model.Result
import lv.zesloka.domain.usecase.user.GetUserStateUseCase

import javax.inject.Inject

open class SplashViewModel: ViewModel() {

    @set:Inject protected lateinit var getUserStateUseCase: GetUserStateUseCase

    private val initInfo: MutableLiveData<String> = MutableLiveData()
    private val initError: MutableLiveData<String> = MutableLiveData()
    private val isErrorPresent: MutableLiveData<Boolean> = MutableLiveData()

    fun init() {
        viewModelScope.launch {
            val result = getUserStateUseCase.runWith(GetUserStateUseCase.Input())


            if (result is Result.Success) {
                showInitInfo(result.data.toString())
            } else {
                val error = result as Result.Error
                showInitError(error.exception.message.toString())
            }
        }
    }

    private fun showInitInfo(info: String) {
        initInfo.postValue(info)
        isErrorPresent.postValue(false)
    }

    private fun showInitError(error: String) {
        initError.postValue(error)
        isErrorPresent.postValue(true)
    }

    fun getInitInfo(): LiveData<String> = initInfo

    fun getInitError(): LiveData<String> = initError

    fun isInitErrorPresent(): LiveData<Boolean> = isErrorPresent

}

