package lv.zesloka.skani.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import lv.zesloka.skani.databinding.FragmentSplashBinding
import lv.zesloka.skani.presentation.vm.SplashViewModel
import lv.zesloka.skani.ui.base.BaseFragment
import lv.zesloka.skani.ui.resource.AppStrings
import javax.inject.Inject

open class SplashFragment : BaseFragment() {

    private lateinit var vm: SplashViewModel
    private lateinit var binding: FragmentSplashBinding

    @Inject
    protected lateinit var strings: AppStrings

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        getAppComponent()?.inject(vm)
        getAppComponent()?.inject(this)
        vm.init()

        vm.getInitInfo().observe(viewLifecycleOwner, { strId ->
            binding.initStateDescription.text = strings.get(strId).get()
        })

        vm.getInitError().observe(viewLifecycleOwner, { error ->
            binding.initError.text = "${strings.get(error.strId).get()}. Error code: ${error.code}"
        })

        vm.isInitErrorPresent().observe(viewLifecycleOwner, Observer<Boolean> { hasError ->
            binding.initError.visibility = if (hasError) View.VISIBLE else View.INVISIBLE
            binding.initStateDescription.visibility =
                if (!hasError) View.VISIBLE else View.INVISIBLE
        })
    }
}