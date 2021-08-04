package lv.zesloka.skani.ui.util

import android.app.Application

fun getResourceString(application: Application, id: Int): String =
    application.getString(id)