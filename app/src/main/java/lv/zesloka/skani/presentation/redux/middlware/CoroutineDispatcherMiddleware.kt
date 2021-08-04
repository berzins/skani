package lv.zesloka.skani.presentation.redux.middlware

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.reduxkotlin.Middleware
import org.reduxkotlin.middleware
import kotlin.coroutines.CoroutineContext

fun <State> coroutineDispatcherMiddleware(context: CoroutineContext): Middleware<State> {
    val scope = CoroutineScope(context)
    return middleware {store, next, action ->
        scope.launch {
            next(action)
        }
    }
}