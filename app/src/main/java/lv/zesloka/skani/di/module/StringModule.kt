package lv.zesloka.skani.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import lv.zesloka.skani.di.module.ApplicationProviderModule
import lv.zesloka.skani.di.qualifiyer.QDefaultInitText
import lv.zesloka.skani.presentation.vm.contract.StringResolver
import lv.zesloka.skani.ui.strings.DefaultInitText

@Module(includes = [ApplicationProviderModule::class])
class StringModule {

    @Provides
    @QDefaultInitText
    fun providesDefaultInitText(app: Application): StringResolver = DefaultInitText(app)

}