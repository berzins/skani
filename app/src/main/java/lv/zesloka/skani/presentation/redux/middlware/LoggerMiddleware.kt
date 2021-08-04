package lv.zesloka.skani.presentation.redux.middlware

import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import org.reduxkotlin.middleware
import timber.log.Timber

val loggerMiddleware = middleware<RdxAppState> { store, next, action ->
    Timber.d("*** DISPATCH acion: $action")
    next(action)
}