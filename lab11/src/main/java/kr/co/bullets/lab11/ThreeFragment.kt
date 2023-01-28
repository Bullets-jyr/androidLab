package kr.co.bullets.lab11.test5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.co.bullets.lab11.databinding.FragmentThreeBinding

class ThreeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentThreeBinding.inflate(inflater, container, false)
        return binding.root
    }
}