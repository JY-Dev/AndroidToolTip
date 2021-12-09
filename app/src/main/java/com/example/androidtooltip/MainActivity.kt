package com.example.androidtooltip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidtooltip.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater,null,false)
        setContentView(binding.root)
        binding.tooltipView.setTooltipText("알라신은 위대하다")
        binding.testButton.setOnClickListener {
            binding.tooltipView.setTooltipVisibility()
        }

    }
}