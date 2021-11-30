package com.example.androidtooltip

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.example.androidtooltip.databinding.ItemPopupBinding

class ToolTipPopup constructor(
    private val context : Context,
    private val text : String
){
    private val popupBinding = ItemPopupBinding.inflate(LayoutInflater.from(context),null,false)
    private val popupWindow = PopupWindow(popupBinding.root,
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT).apply {
        isOutsideTouchable = true
        popupBinding.tooltipTextView.text = text

    }
    fun showAlignTop(anchor: View) {
        popupBinding.root.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        println("너비 : ${popupBinding.tooltipTextView.measuredHeight}")
        popupWindow.showAsDropDown(
            anchor,
            0,
            -popupBinding.root.measuredHeight - anchor.measuredHeight
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