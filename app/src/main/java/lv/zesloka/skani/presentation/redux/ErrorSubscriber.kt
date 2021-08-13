package lv.zesloka.skani.presentation.redux

import lv.zesloka.domain.usecase.base.ErrorCode
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import lv.zesloka.skani.presentation.redux.state.error.RdxError
import org.reduxkotlin.Store

typealias OnNewError = (errorCode: Int) -> Unit
typealias ErrorSelector = (state: RdxAppState) -> RdxError

interface ErrorSubscriber : DisposableSubscriber {
    fun doOnError(funOnError: OnNewError)
}

open class BaseErrorSubscriber(
    private val store: Store<RdxAppState>,
    private val selectError: ErrorSelector
) : ErrorSubscriber {

    companion object {
        const val VERSION_NOT_SET = Int.MIN_VALUE
    }

    private var version = VERSION_NOT_SET

    private var handleError: OnNewError = {}

    private val subscription: () -> Unit = store.subscribe {
        val error = selectError(store.state)
        if (version != error.version && error.code != ErrorCode.NONE) {
            version = error.version
            handleError(error.code)
        }
    }

    override fun doOnError(funOnError: OnNewError) {
        handleError = funOnError
    }

    override fun dispose() {
        subscription() // that's how you unsubscribe :)
    }

}