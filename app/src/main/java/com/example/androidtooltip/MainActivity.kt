package com.example.androidtooltip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidtooltip.databinding.ActivityMainBinding
import com.example.androidtooltip.databinding.ItemPopupBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var popupBinding: ItemPopupBinding
    lateinit var toolTipPopup : ToolTipPopup
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater,null,false)
        popupBinding = ItemPopupBinding.inflate(layoutInflater,binding.root,false)
        setContentView(binding.root)
        toolTipPopup = ToolTipPopup(context = this,"sssaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaasfsfdsdfsfd\nsfd\nsdfa`\na")
        binding.testButton.setOnClickListener {
            toolTipPopup.showAlignTop(binding.testButton)
        }
    }
}