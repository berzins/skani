package lv.zesloka.skani.ui.base


import androidx.fragment.app.Fragment
import lv.zesloka.skani.SkaniApplication
import lv.zesloka.skani.di.AppComponent

open class BaseFragment : Fragment() {

    protected fun getAppComponent(): AppComponent? =
        activity?.let {
            (it.application as SkaniApplication).getAppComponent()
        }






}