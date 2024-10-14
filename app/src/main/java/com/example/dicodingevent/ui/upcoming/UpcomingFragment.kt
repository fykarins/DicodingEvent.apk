package com.example.dicodingevent.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.EventAdapter
import com.example.dicodingevent.databinding.FragmentUpcomingBinding

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!

    private lateinit var eventAdapter: EventAdapter
    private lateinit var upcomingViewModel: UpcomingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)

        // Setup ViewModel
        upcomingViewModel = ViewModelProvider(this).get(UpcomingViewModel::class.java)

        // Setup RecyclerView
        binding.rvUpcomingEvents.layoutManager = LinearLayoutManager(requireContext())
        eventAdapter = EventAdapter() // Tidak perlu daftar kosong di sini
        binding.rvUpcomingEvents.adapter = eventAdapter

        // Observe ViewModel untuk mendapatkan data event
        observeEvents()

        return binding.root
    }

    private fun observeEvents() {
        upcomingViewModel.upcomingEvents.observe(viewLifecycleOwner, Observer { events ->
            if (events != null && events.isNotEmpty()) {
                eventAdapter.submitList(events) // Mengirimkan daftar event ke adapter
            } else {
                Toast.makeText(requireContext(), "No events available", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
