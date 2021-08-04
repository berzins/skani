package lv.zesloka.skani.presentation.redux.action

sealed class InitActions {
    sealed class OnStart{
        class Init()
        class InitDone()
        class InitFailed(code: Int, message: String)
    }
}