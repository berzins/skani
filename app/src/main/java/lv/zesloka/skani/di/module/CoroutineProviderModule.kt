package lv.zesloka.skani.di.module

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import lv.zesloka.skani.di.qualifiyer.IOCoroutineContext
import lv.zesloka.skani.di.qualifiyer.UICoroutineContext
import kotlin.coroutines.CoroutineContext

@Module
class CoroutineProviderModule {

    @Provides
    @IOCoroutineContext
    fun provideIOCoroutineContext(): CoroutineContext = Dispatchers.IO

    @Provides
    @UICoroutineContext
    fun provideUiCoroutineContext(): CoroutineContext = Dispatchers.Main
}