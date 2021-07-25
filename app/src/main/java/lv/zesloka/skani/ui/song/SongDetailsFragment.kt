package lv.zesloka.skani.ui.song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import lv.zesloka.skani.databinding.FragmentSongDetailsBinding
import lv.zesloka.skani.presentation.vm.SongViewModel
import lv.zesloka.skani.ui.base.BaseFragment

class SongDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentSongDetailsBinding
    private lateinit var vm: SongViewModel

    private val titleObserver = Observer<String> { title ->
        binding.title.setText(title)
    }

    private val contentObserver = Observer<String> { content ->
        binding.content.setText(content)
    }

    private val closeObserver = Observer<Boolean> { shouldClose ->
        if (shouldClose) {
            Navigation.findNavController(binding.root).popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongDetailsBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = ViewModelProviders.of(this).get(SongViewModel::class.java)
        getAppComponent()?.inject(vm)

        vm = ViewModelProviders.of(this).get(SongViewModel::class.java)
        vm.title.observe(viewLifecycleOwner, titleObserver)
        vm.content.observe(viewLifecycleOwner, contentObserver)
        binding.floatingActionButton.setOnClickListener {
             vm.saveSong(
                 binding.title.text.toString(),
                 binding.content.text.toString()
             )
        }
    }
}