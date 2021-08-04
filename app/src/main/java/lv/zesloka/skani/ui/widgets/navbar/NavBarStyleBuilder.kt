package lv.zesloka.skani.ui.widgets.navbar


abstract class Modifier<T> {

    private var next: Modifier<T>? = null

    fun add(modifier: Modifier<T>): Modifier<T> {
        modifier.next = this
        return modifier
    }

    fun applyTo(t: T): T {
        return if (next != null) {
            modify(next?.applyTo(t)!!)
        } else {
            modify(t)
        }
    }

    protected abstract fun modify(t: T): T
}

abstract class NavBarDecorator : Modifier<NavigationBar>()

class ClearNavBar : NavBarDecorator() {
    override fun modify(navBar: NavigationBar): NavigationBar {
        clearLeftAction(navBar)
        clearRightAction(navBar)
        navBar.setTitle("")
        return navBar
    }

    private fun clearLeftAction(navBar: NavigationBar) {
        navBar.showLeftAction(false)
        navBar.setLeftActionListener { }
    }

    private fun clearRightAction(navBar: NavigationBar) {
        navBar.showRightAction(false)
        navBar.setRightActionListener { }
    }
}

class SetLeftAction(private val drawableId: Int, private val listener: ActionListener) :
    NavBarDecorator() {
    override fun modify(navBar: NavigationBar): NavigationBar {
        navBar.setLeftActionListener(listener)
        navBar.setLeftActionIcon(drawableId)
        navBar.showLeftAction(true)
        return navBar
    }
}

class SetRightAction(private val drawableId: Int, private val listener: ActionListener) :
    NavBarDecorator() {
    override fun modify(navBar: NavigationBar): NavigationBar {
        navBar.setRightActionListener(listener)
        navBar.setRightActionIcon(drawableId)
        navBar.showRightAction(true)
        return navBar
    }
}

class SetTitle(private val title: String) : NavBarDecorator() {
    override fun modify(navBar: NavigationBar): NavigationBar {
        navBar.setTitle(title)
        return navBar
    }
}




