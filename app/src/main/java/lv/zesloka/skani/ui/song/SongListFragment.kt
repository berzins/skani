package lv.zesloka.skani.ui.song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import lv.zesloka.skani.databinding.FragmentSongListBinding
import lv.zesloka.skani.ui.base.BaseFragment

class SongListFragment : BaseFragment() {

    private lateinit var binding: FragmentSongListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongListBinding.inflate(LayoutInflater.from(context))
        init()
        return binding.root
    }

    private fun init() {
        binding.buttonAddSong.setOnClickListener{goToSongDetails()}
    }

    private fun goToSongDetails(id: Long = 0L) {
        val action = SongListFragmentDirections.actionSongListFragmentToSongDetailsFragment(id)
        Navigation.findNavController(binding.root).navigate(action)
    }

}