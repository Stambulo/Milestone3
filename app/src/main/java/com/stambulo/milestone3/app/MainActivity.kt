package com.stambulo.milestone3.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stambulo.milestone3.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        // Main TODO`s
        //TODO: design of adapters, grouping and etc.
    }
}
