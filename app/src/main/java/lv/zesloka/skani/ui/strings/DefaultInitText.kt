package lv.zesloka.skani.ui.strings

import android.app.Application
import lv.zesloka.skani.R
import lv.zesloka.skani.presentation.vm.contract.StringResolver
import lv.zesloka.skani.ui.util.getResourceString

class DefaultInitText(private val app: Application): StringResolver {
    override fun get(): String  = getResourceString(app, R.string.txt_initialize)

}