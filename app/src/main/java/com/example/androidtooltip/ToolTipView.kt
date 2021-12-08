package com.example.androidtooltip

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.androidtooltip.databinding.ItemHeadBinding
import com.example.androidtooltip.databinding.ItemPopupBinding
import com.example.androidtooltip.databinding.ItemTooltipBinding

class ToolTipView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null) :
    LinearLayout(context, attributeSet) {
    private val binding = ItemTooltipBinding.inflate(LayoutInflater.from(context),null,false)
    private val triangleBinding = ItemHeadBinding.inflate(LayoutInflater.from(context),null,false)
        init {
            addView(binding.root)
            val test = binding.tooltipText.viewTreeObserver.addOnGlobalLayoutListener {
                val width = binding.tooltipText.width
                val height = binding.tooltipText.height
                triangleBinding.triangle.x = width/2.toFloat() - triangleBinding.triangle.width/2
                binding.tooltipText.y = triangleBinding.triangle.height.toFloat()
            }

        }
}