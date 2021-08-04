package lv.zesloka.skani.ui.base

interface BackNavigationOwner {
    fun overrideBackWith(backNavigationListener: BackNavigationListener)
}