package lv.zesloka.skani.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import lv.zesloka.skani.R
import lv.zesloka.skani.databinding.FragmentLoginBinding
import lv.zesloka.skani.presentation.vm.auth.LoginViewModel
import lv.zesloka.skani.ui.MainActivity
import lv.zesloka.skani.ui.base.BaseFragment
import lv.zesloka.skani.ui.widgets.navbar.*
import javax.inject.Inject

open class LoginFragment : BaseFragment() {

    @Inject
    protected lateinit var navBarOwner: NavigationBarOwner

    private lateinit var binding: FragmentLoginBinding
    private lateinit var vm: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).showNavBar()
        vm = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        inject()
        vm.init()

        setupNavBar(navBarOwner.getNavBar())
        binding.actionRegister.setOnClickListener {
            vm.onRegisterAction()
        }
        binding.actionLogin.setOnClickListener {
            vm.onLoginAction(
                username = binding.username.getInput(),
                password = binding.password.getInput()
            )
        }
    }

    private fun inject() {
        getAppComponent()?.inject(vm)
        getAppComponent()?.inject(this)
    }

    private fun setupNavBar(navBar: NavigationBar) {
        ClearNavBar()
            .add(SetLeftAction(R.drawable.ic_back_24) {activity?.onBackPressed()})
            .add(SetTitle("Login"))
            .applyTo(navBar)
    }
}