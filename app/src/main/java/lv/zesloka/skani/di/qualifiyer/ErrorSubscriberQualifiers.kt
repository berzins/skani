package lv.zesloka.skani.di.qualifiyer

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class QSignInErrorSubscriber

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RegisterErrorSubscriber