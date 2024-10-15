package com.example.dicodingevent

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.dicodingevent.databinding.ActivityMainBinding
import com.example.dicodingevent.ui.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        mainViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        recyclerViewAdapter = RecyclerViewAdapter()
        recyclerViewAdapter.setOnItemClickCallback(object : RecyclerViewAdapter.OnItemClickCallback {
            override fun onItemClicked(eventId: String) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("EVENT_ID", eventId)
                startActivity(intent)
            }
        })

        binding.recyclerView.adapter = recyclerViewAdapter
    }

    override fun onResume() {
        super.onResume()
       }
}
