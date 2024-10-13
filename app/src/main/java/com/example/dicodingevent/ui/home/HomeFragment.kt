package com.example.dicodingevent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingevent.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Mengambil ViewModel
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // Meng-inflate layout untuk fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Mengambil root view
        val root: View = binding.root

        // Menghubungkan TextView dengan data dari ViewModel
        homeViewModel.text.observe(viewLifecycleOwner) {
            binding.textHome.text = it
        }

        // Mengembalikan root view
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Menghindari memory leak
    }
}
