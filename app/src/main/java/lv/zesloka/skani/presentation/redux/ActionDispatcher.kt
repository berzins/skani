package lv.zesloka.skani.presentation.redux

import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import org.reduxkotlin.Store

interface ActionDispatcher {
    fun dispatch(action: Any)
}

/**
 * Just an abstraction layer around redux stuff
 * Might be useful. HA HA.
 */
open class BaseAppActionDispatcher(
    private val store: Store<RdxAppState>
): ActionDispatcher {

    override fun dispatch(action: Any) {
        store.dispatch(action)
    }
}
