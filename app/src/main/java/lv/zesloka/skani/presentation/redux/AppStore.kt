package lv.zesloka.skani.presentation.redux

import lv.zesloka.skani.presentation.redux.reducer.appReducer
import lv.zesloka.skani.presentation.redux.state.RdxAppState
import lv.zesloka.skani.presentation.redux.state.extentions.initial
import org.reduxkotlin.createThreadSafeStore

val appStore = createThreadSafeStore(
    reducer = ::appReducer, preloadedState = RdxAppState.initial()
)