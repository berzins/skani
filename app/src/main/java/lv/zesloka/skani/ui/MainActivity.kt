package lv.zesloka.skani.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import lv.zesloka.skani.R
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.motion.widget.MotionLayout
import lv.zesloka.skani.ui.base.BackNavigationListener
import lv.zesloka.skani.ui.base.BackNavigationOwner
import lv.zesloka.skani.ui.widgets.navbar.NavigationBar
import lv.zesloka.skani.ui.widgets.navbar.NavigationBarOwner

class MainActivity : AppCompatActivity(), NavigationBarOwner, BackNavigationOwner {

    private lateinit var motionLayout: MotionLayout
    private lateinit var navBar: NavigationBar
    private var onBackListener: BackNavigationListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        motionLayout = findViewById(R.id.motion_layout)
        navBar = findViewById(R.id.navigation_bar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nav_bar, menu)
        return true
    }

    /* NAV BAR OWNER */

    override fun showNavBar() {
        motionLayout.transitionToEnd()
    }

    override fun hideNavBar() {
        motionLayout.transitionToEnd()
    }

    override fun getNavBar(): NavigationBar = navBar

    /* END OF NAV BAR OWNER */

    override fun onBackPressed() {
        onBackListener?.let {
            if (it.onBack()) {
                super.onBackPressed()
            }
            return
        }
        super.onBackPressed()
    }

    override fun overrideBackWith(backNavigationListener: BackNavigationListener) {
        onBackListener = backNavigationListener
    }
}