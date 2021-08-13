package lv.zesloka.skani.presentation.redux

import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import org.reduxkotlin.Store
import timber.log.Timber
import java.lang.Exception

typealias StateSelector = (state: RdxAppState) -> Any?

typealias RenderFun = (state: RdxAppState) -> Unit

interface AppStoreSubscriber: DisposableSubscriber {
    fun onRender(render: RenderFun)
}

/**
 * Listens on store state updates and triggers render function on subscriber.
 *
 * The intention is to use this class with vm(s) what render ui(s)
 * It's more like bridge between redux store and vms where some specific trigger
 * functionallity might be implemented.
 * For example see -> StoreSubscriberWithStateCompare
 */
open class BaseStoreSubscriber(
    private val store: Store<RdxAppState>
) : AppStoreSubscriber {
    private var state: RdxAppState = store.state
    private var firstRun = true
    private var subscription: () -> Unit = {}

    private fun subscribeWith(render: RenderFun) {
        subscription = store.subscribe {
            try {
                val newState = store.state

                if(firstRun) {
                    firstRun = false
                    render(newState)
                } else {
                    if (shouldRender(state, newState)) {
                        render(newState)
                    }
                }
                state = newState
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    protected open fun shouldRender(current: RdxAppState, next: RdxAppState): Boolean {
        return true
    }

    override fun onRender(render: RenderFun) {
        subscribeWith(render)
    }

    override fun dispose() {
        subscription()
    }
}

/**
 * The render function triggered ony if states of interest have been changed.
 */
class StoreSubscriberWithStateCompare(
    store: Store<RdxAppState>,
    private val stateSelectors: List<StateSelector>
) : BaseStoreSubscriber(store) {

    override fun shouldRender(current: RdxAppState, next: RdxAppState): Boolean {
        val selector = stateSelectors.firstOrNull { it(current) != it(next) }
        return selector != null
    }
}

