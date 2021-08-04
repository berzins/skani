package lv.zesloka.skani.di.qualifiyer

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class QSplashStoreSubscriber

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class QLoginStoreSubscriber

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class QRegisterStoreSubscriber