package lv.zesloka.skani.presentation.redux

import kotlinx.coroutines.Dispatchers
import lv.zesloka.skani.presentation.redux.middlware.coroutineDispatcherMiddleware
import lv.zesloka.skani.presentation.redux.middlware.loggerMiddleware
import lv.zesloka.skani.presentation.redux.reducer.appReducer
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import lv.zesloka.skani.presentation.redux.state.app.initial
import org.reduxkotlin.*


//val appStore = createStore(
//    reducer = combineReducers(::appReducer),
//    preloadedState = RdxAppState.initial(),
//    enhancer = compose(listOf(
//        applyMiddleware(
//            coroutineDispatcherMiddleware(Dispatchers.IO),
//            loggerMiddleware,
//            createThunkMiddleware(
//
//            )
//
//
//        )
//    ))
//)