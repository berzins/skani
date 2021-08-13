package lv.zesloka.skani.di.module

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import dagger.Module
import dagger.Provides
import lv.zesloka.skani.presentation.vm.contract.ResolverProvider
import lv.zesloka.skani.ui.resource.AppStrings
import lv.zesloka.skani.ui.widgets.dialog.DialogBuilderOwner

@Module(includes = [ApplicationProviderModule::class])
class ResourceProviderModule {


    @Provides
    fun provideAppStrings(app: Application): ResolverProvider<String> =
        AppStrings(app)

    @Provides
    fun providesDialogBuilderOwner(activity: Activity?): DialogBuilderOwner =
        object : DialogBuilderOwner {
            override fun getBuilder(): AlertDialog.Builder? =
                activity?.let {
                    AlertDialog.Builder(activity)
                }

        }
}