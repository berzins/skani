package lv.zesloka.skani.ui.song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import lv.zesloka.skani.R
import lv.zesloka.skani.databinding.FragmentSongListBinding
import lv.zesloka.skani.presentation.redux.ActionDispatcher
import lv.zesloka.skani.presentation.redux.action.UserActions
import lv.zesloka.skani.presentation.vm.HomeViewModel
import lv.zesloka.skani.ui.base.BaseFragment
import lv.zesloka.skani.ui.widgets.navbar.*
import okhttp3.Dispatcher
import javax.inject.Inject

open class HomeFragment : BaseFragment() {

    @Inject
    protected lateinit var navBarOwner: NavigationBarOwner

    @Inject
    protected lateinit var dispatcher: ActionDispatcher

    private lateinit var binding: FragmentSongListBinding
    private lateinit var vm: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongListBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        inject()
        setUpNavBar(navBarOwner.getNavBar())
        navBarOwner.showNavBar()
    }

    private fun inject() {
        getAppComponent()?.inject(this)
        getAppComponent()?.inject(vm)
    }

    private fun setUpNavBar(navBar: NavigationBar) {
        ClearNavBar()
            .add(SetTitle("Skani"))
            .add(SetRightAction(R.drawable.ic_logout_24) {
                dispatcher.dispatch(UserActions.SignOut.Start())
            })
            .applyTo(navBar)
    }

}