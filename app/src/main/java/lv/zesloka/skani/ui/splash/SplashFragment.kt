package lv.zesloka.skani.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import lv.zesloka.skani.databinding.FragmentSplashBinding
import lv.zesloka.skani.presentation.vm.SplashViewModel
import lv.zesloka.skani.ui.base.BaseFragment

class SplashFragment : BaseFragment() {

    private lateinit var vm: SplashViewModel
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        getAppComponent()?.inject(vm)
        vm.init()

        vm.getInitInfo().observe(viewLifecycleOwner, Observer<String> { info ->
            binding.initStateDescription.text = info
        })

        vm.getInitError().observe(viewLifecycleOwner, Observer<String> { error ->
            binding.initError.text = error
        })

        vm.isInitErrorPresent().observe(viewLifecycleOwner, Observer<Boolean> { hasError ->
            binding.initError.visibility = if (hasError) View.VISIBLE else View.INVISIBLE
            binding.initStateDescription.visibility = if (!hasError) View.VISIBLE else View.INVISIBLE
        })


//        activity?.applicationContext?.let {
//            Amplify.addPlugin(AWSCognitoAuthPlugin())
//            Amplify.configure(it)
//
//            Amplify.Auth.fetchAuthSession(
//                {
//                    Timber.d("Auth session = '$it'")
//                },
//                {
//                    Timber.e(it.toString())
//                }
//            )
//        }
    }
}