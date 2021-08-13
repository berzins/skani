package lv.zesloka.skani.presentation.redux.middlware

import lv.zesloka.skani.presentation.model.navigation.Screen
import lv.zesloka.skani.presentation.redux.action.NavigationActions
import lv.zesloka.skani.presentation.redux.action.UserActions
import lv.zesloka.skani.presentation.redux.middlware.thunk.NavigationThunks
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import org.reduxkotlin.Middleware
import org.reduxkotlin.middleware

fun navigationMiddleware(navigationThunks: NavigationThunks): Middleware<RdxAppState> = middleware { store, next, action ->
    val result = next(action)
    when (action) {
        is NavigationActions.StartNavigation -> {
            store.dispatch(navigationThunks.navigateWith(action))
        }
    }
    result
}
