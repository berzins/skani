package lv.zesloka.skani.ui.base


import androidx.fragment.app.Fragment
import lv.zesloka.skani.SkaniApplication
import lv.zesloka.skani.di.AppComponent
import lv.zesloka.skani.presentation.redux.ActionDispatcher
import lv.zesloka.skani.presentation.redux.action.NavigationActions
import lv.zesloka.skani.ui.MainActivity

abstract class BaseFragment : Fragment(), BackNavigationListener {

    protected fun getAppComponent(): AppComponent? =
        activity?.let {
            (it.application as SkaniApplication).getAppComponent()
        }

    override fun onResume() {
        super.onResume()
        activity?.let {
            (it as MainActivity).overrideBackWith(this)
        }
    }

    override fun onBack(): Boolean {
        // a place to do something on back..
        // or just block it and let the screens to override this if necessary
        return false
    }
}