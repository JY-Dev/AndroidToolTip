package com.example.androidtooltip

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.Toast
import com.example.androidtooltip.databinding.ItemPopupBinding

class ToolTipPopup constructor(
    context : Context,
    private val text : String
){
    private val popupBinding = ItemPopupBinding.inflate(LayoutInflater.from(context),null,false)
    private var textViewHeight = 0
    private var popupWindowHeight = 0
    private val popupWindow = PopupWindow(popupBinding.root,
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT).apply {
        isOutsideTouchable = true
        popupBinding.tooltipTextView.text = text
    }
    init {
        popupBinding.tooltipWrapper.setOnClickListener {
            popupWindow.dismiss()
        }
        popupBinding.root.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        popupBinding.tooltipTextView.paint.measureText(popupBinding.tooltipTextView.text.toString())
        popupWindowHeight = popupBinding.root.measuredHeight
        textViewHeight = popupBinding.tooltipTextView.measuredHeight
    }

    fun showAlignTop(anchor: View) {
        popupWindow.showAsDropDown(
            anchor,
            0,
            -popupWindowHeight - anchor.measuredHeight - textViewHeight
        )
    }

    fun showAlignBottom(anchor: View) {
        popupWindow.showAsDropDown(
            anchor,
            0,
            0
        )
    }

    private fun show(anchor: View){

    }
}