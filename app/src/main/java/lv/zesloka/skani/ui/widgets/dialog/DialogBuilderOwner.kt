package lv.zesloka.skani.ui.widgets.dialog

import android.app.AlertDialog

interface DialogBuilderOwner {
    fun getBuilder(): AlertDialog.Builder?
}