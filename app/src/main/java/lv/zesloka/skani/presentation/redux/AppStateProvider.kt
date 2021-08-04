package lv.zesloka.skani.presentation.redux

import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import org.reduxkotlin.Store

interface AppStateProvider<T> {
    fun getState(): T
}

class AppStateProviderImpl(private var store: Store<RdxAppState>) :
    AppStateProvider<RdxAppState> {
    override fun getState(): RdxAppState {
        return store.state
    }

}