package lv.zesloka.skani.ui.resource

import android.app.Application
import lv.zesloka.skani.presentation.vm.contract.Resolver

open class ResourceStrResolver constructor(
    private val app: Application,
    private val resId: Int
) : Resolver<String> {
    override fun get(): String {
        return app.resources.getString(resId)
    }
}