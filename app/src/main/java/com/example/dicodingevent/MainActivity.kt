package com.example.dicodingevent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dicodingevent.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate menggunakan ActivityMainBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Toolbar sebagai ActionBar
        setSupportActionBar(binding.toolbar)

        // Set up BottomNavigationView
        val navView: BottomNavigationView = binding.navView

        // Set up NavHostFragment untuk navigasi
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        // Mengonfigurasi AppBar
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_upcoming,
                R.id.navigation_finished
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Menghubungkan BottomNavigationView dengan NavController
        navView.setupWithNavController(navController)
    }

    // Opsional: untuk navigasi up button
    override fun onSupportNavigateUp(): Boolean {
        val navController = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)!!.findNavController()
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
