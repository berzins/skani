package lv.zesloka.skani.di.qualifiyer

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AWSUser

/* COROUTINE */

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IOCoroutineContext

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UICoroutineContext

/* MIDDLEWARE */

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UserActionMiddleware

/* SCREEN NAVIGATOR */

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class QSplashNavigator

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class QLoginNavigator

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class QRegisterNavigator

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class QDefaultAppNavigators



