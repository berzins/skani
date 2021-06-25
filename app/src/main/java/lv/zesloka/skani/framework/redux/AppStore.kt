package lv.zesloka.skani.framework.redux

import lv.zesloka.skani.framework.redux.reducer.appReducer
import lv.zesloka.skani.framework.redux.state.AppState
import org.reduxkotlin.createThreadSafeStore

val appStore = createThreadSafeStore(
    reducer = ::appReducer, preloadedState = AppState()
)