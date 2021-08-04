package lv.zesloka.skani.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import lv.zesloka.skani.R
import lv.zesloka.skani.databinding.FragmentRegisterBinding
import lv.zesloka.skani.presentation.redux.ActionDispatcher
import lv.zesloka.skani.presentation.redux.state.auth.RdxSignUpStage
import lv.zesloka.skani.presentation.vm.RegistrationViewModel
import lv.zesloka.skani.ui.base.BaseFragment
import lv.zesloka.skani.ui.widgets.navbar.*
import javax.inject.Inject

open class RegisterFragment : BaseFragment() {

    @Inject
    protected lateinit var navBarOwner: NavigationBarOwner

    @Inject
    protected lateinit var dispatcher: ActionDispatcher

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var vm: RegistrationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders.of(this).get(RegistrationViewModel::class.java)
        inject()
        initNavBar(navBarOwner.getNavBar())
        vm.init()

        vm.getAuthStage().observe(viewLifecycleOwner, Observer {
            when (it) {
                RdxSignUpStage.SIGN_UP -> {
                    binding.stageContainer.transitionToState(R.id.collect_data)
                }
                RdxSignUpStage.VERIFY_EMAIL -> {
                    binding.stageContainer.transitionToState(R.id.verify_email)
                }
                RdxSignUpStage.DONE -> {
                    binding.stageContainer.transitionToState(R.id.done)
                }
            }
        })

        binding.actionRegister.setOnClickListener {
            val username = binding.username.getInput()
            val email = binding.email.getInput()
            val password = binding.password.getInput()
            vm.onRegisterAction(username, email, password)
        }

        binding.actionVerifyCode.setOnClickListener {
            val code = binding.inputVerifyCode.getInput()
            vm.onVerifyCode(code)
        }

        binding.actionComplete.setOnClickListener {
            vm.onOnComplete()
        }

    }

    private fun inject() {
        getAppComponent()?.inject(this)
        getAppComponent()?.inject(vm)
    }

    private fun initNavBar(navBar: NavigationBar) {
        ClearNavBar()
            .add(SetLeftAction(R.drawable.ic_back_24) { vm.onBack() })
            .add(SetTitle("Register"))
            .applyTo(navBar)
    }

    override fun onBack(): Boolean {
        vm.onBack()
        return false
    }
}