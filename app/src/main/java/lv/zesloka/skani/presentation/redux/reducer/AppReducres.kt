package lv.zesloka.skani.presentation.redux.reducer

import lv.zesloka.skani.presentation.redux.state.app.RdxAppState

fun appReducer(state: RdxAppState, action: Any): RdxAppState {
    return RdxAppState(
        songDetailsState = songDetailsReducer(state, action),
        userState = userReducer(state, action),
        navigationState = navigationReducer(state, action),
        authState = authReducer(state, action)
    )
}