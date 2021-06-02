package lv.zesloka.skani.presentation.song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import lv.zesloka.skani.databinding.FragmentSongDetailsBinding
import lv.zesloka.skani.databinding.FragmentSongListBinding
import lv.zesloka.skani.presentation.base.BaseFragment

class SongDetailsFragment : BaseFragment() {

    private lateinit var binding: FragmentSongDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongDetailsBinding.inflate(LayoutInflater.from(context))
        return binding. root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener {
            Navigation.findNavController(binding.root).popBackStack()
        }
    }
}