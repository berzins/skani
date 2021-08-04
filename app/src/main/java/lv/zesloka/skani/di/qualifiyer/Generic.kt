package lv.zesloka.skani.di.qualifiyer

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class QUserName

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class QPassword

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class QEmail
