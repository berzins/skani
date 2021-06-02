package lv.zesloka.skani.presentation.song

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import lv.zesloka.skani.R
import lv.zesloka.skani.databinding.FragmentSongListBinding
import lv.zesloka.skani.presentation.base.BaseFragment

class SongListFragment : BaseFragment() {

    private lateinit var binding: FragmentSongListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongListBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

}