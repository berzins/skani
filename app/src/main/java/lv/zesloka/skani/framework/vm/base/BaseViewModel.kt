package lv.zesloka.skani.framework.vm.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import lv.zesloka.skani.framework.redux.appStore
import lv.zesloka.skani.framework.redux.state.AppState
import org.reduxkotlin.Store

class BaseViewModel<T>(application: Application) : AndroidViewModel(application) {
    protected val state: T
    protected val store: Store<AppState> = appStore

    init {


    }

}