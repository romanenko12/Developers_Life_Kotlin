package com.example.developerslifekotlin.gifview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.developerslifekotlin.DevelopersLifeApplication
import com.example.developerslifekotlin.databinding.FragmentGifviewBinding
import com.google.android.material.tabs.TabLayout

class GifViewFragment : Fragment() {

    private val viewModel: GifViewViewModel by lazy {
        val appContainer = (requireActivity().application as DevelopersLifeApplication).appContainer
        ViewModelProvider(this, GifViewViewModelFactory(appContainer.userRepository))[GifViewViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentGifviewBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.chooseCategory(tab!!.position)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        return binding.root
    }
}