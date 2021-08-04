package lv.zesloka.skani.ui.widgets.navbar

import javax.inject.Inject

open class BaseNavBarController @Inject constructor(private val navBarOwner: NavigationBarOwner)
    : NavBarController  {

    override fun setTitle(title: String) {
        navBarOwner.getNavBar().setTitle(title)
    }

    override fun show() {
        navBarOwner.showNavBar()
    }

    override fun hide() {
        navBarOwner.hideNavBar()
    }
}