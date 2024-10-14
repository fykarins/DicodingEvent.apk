package com.example.dicodingevent

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.dicodingevent.databinding.ActivityMainBinding
import com.example.dicodingevent.ui.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Inisialisasi ViewModel menggunakan delegasi by viewModels
    private val mainViewModel: MainViewModel by viewModels()

    // Deklarasi navController
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout dengan menggunakan ActivityMainBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Toolbar sebagai ActionBar
        setSupportActionBar(binding.toolbar)

        // Mendapatkan NavController dari NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        // Mengamati loading state dari ViewModel untuk menampilkan ProgressBar
        mainViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onResume() {
        super.onResume()

        val navigateTo = intent.getStringExtra("navigateTo")
        if (navigateTo == "home") {
            navController.navigate(R.id.navigation_home)
        }
    }
}
