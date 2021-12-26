package io.astronout.pokepedia.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import io.astronout.pokepedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupNavHost()
    }

    private fun setupNavHost() {
        val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostMain.id) as NavHostFragment
        navController = navHostFragment.navController
    }
}