package lv.zesloka.skani.ui.widgets.navbar

interface NavigationBarOwner {
    fun showNavBar()
    fun hideNavBar()
    fun getNavBar(): NavigationBar
}