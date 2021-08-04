package lv.zesloka.skani.di.module

import dagger.Module
import dagger.Provides
import lv.zesloka.skani.di.qualifiyer.*
import lv.zesloka.skani.presentation.redux.*
import lv.zesloka.skani.presentation.redux.middlware.*
import lv.zesloka.skani.presentation.redux.middlware.thunk.AppInitThunks
import lv.zesloka.skani.presentation.redux.middlware.thunk.AuthInputValidationThunks
import lv.zesloka.skani.presentation.redux.middlware.thunk.NavigationThunks
import lv.zesloka.skani.presentation.redux.middlware.thunk.UserThunks
import lv.zesloka.skani.presentation.redux.reducer.appReducer
import lv.zesloka.skani.presentation.redux.state.app.RdxAppState
import lv.zesloka.skani.presentation.redux.state.app.initial
import lv.zesloka.skani.presentation.redux.state.auth.RdxAuthState
import lv.zesloka.skani.presentation.redux.state.auth.selectFrom
import lv.zesloka.skani.presentation.redux.state.user.RdxUserState
import lv.zesloka.skani.presentation.redux.state.user.selectFrom
import org.reduxkotlin.*
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module(includes = [UserModule::class, CoroutineProviderModule::class, ScreenNavigatorModule::class])
class StoreModule {

    @Provides
    @Singleton
    fun provideStore(
        @IOCoroutineContext ioContext: CoroutineContext,
        @UICoroutineContext uiContext: CoroutineContext,
        userThunks: UserThunks,
        navigationThunks: NavigationThunks,
        authInputValidationThunks: AuthInputValidationThunks,
        appInitThunks: AppInitThunks
    ): Store<RdxAppState> = createThreadSafeStore(
        reducer = combineReducers(::appReducer),
        preloadedState = RdxAppState.initial(),
        enhancer = compose(
            listOf(
//                presenterEnhancer(uiContext),
                applyMiddleware(
                    coroutineDispatcherMiddleware(ioContext),
                    loggerMiddleware,
                    createThunkMiddleware(),
                    initializationMiddleware(appInitThunks),
                    userActionMiddleware(userThunks),
                    navigationMiddleware(navigationThunks),
                    validationMiddleware(authInputValidationThunks)
                )
            )
        )
    )

    @Provides
    fun provideDispatcher(store: Store<RdxAppState>): ActionDispatcher =
        BaseAppActionDispatcher(store)

    @Provides
    @QSplashStoreSubscriber
    fun providesSplashStoreSubscriber(store: Store<RdxAppState>): AppStoreSubscriber =
        StoreSubscriberWithStateCompare(
            store = store,
            stateSelectors = listOf { state -> RdxUserState.selectFrom(state) }
        )

    @Provides
    @QLoginStoreSubscriber
    fun providesLoginStoreSubscriber(store: Store<RdxAppState>): AppStoreSubscriber =
        StoreSubscriberWithStateCompare(
            store = store,
            stateSelectors = listOf { state -> RdxUserState.selectFrom(state) }
        )

    @Provides
    @QRegisterStoreSubscriber
    fun providesRegisterStoreSubscriber(store: Store<RdxAppState>): AppStoreSubscriber =
        StoreSubscriberWithStateCompare(
            store = store,
            stateSelectors = listOf { state -> RdxAuthState.selectFrom(state) }
        )

    @Provides
    fun providesBaseStoreSubscriber(store: Store<RdxAppState>): AppStoreSubscriber =
          BaseStoreSubscriber(store)

    @Provides
    fun providesAppStateProvider(store: Store<RdxAppState>): AppStateProvider<RdxAppState> =
        AppStateProviderImpl(store)
}