package lv.zesloka.skani.presentation.redux.middlware

import lv.zesloka.skani.presentation.redux.action.InitActions
import lv.zesloka.skani.presentation.redux.action.UserActions
import lv.zesloka.skani.presentation.redux.middlware.thunk.AppInitThunks
import lv.zesloka.skani.presentation.redux.middlware.thunk.UserThunks
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import org.reduxkotlin.middleware

fun initializationMiddleware(appInitThunks: AppInitThunks) = middleware<RdxAppState> { store, next, action ->
    val dispatch = store.dispatch
    val result = next(action)
    when (action) {
        is InitActions.OnStart.Init -> dispatch(appInitThunks.initOnStart())
    }
    result
}