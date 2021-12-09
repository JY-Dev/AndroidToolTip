package com.example.androidtooltip

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet

class TimerTooltipView @JvmOverloads constructor(context : Context, attrs : AttributeSet? = null) : ToolTipView(context,attrs) {
    private val timerInterval = 1000L
    private val attr =
        context.theme.obtainStyledAttributes(attrs, R.styleable.TimerTooltip, 0, 0)
    private val timerSecond = attr.getInt(R.styleable.TimerTooltip_timerSecond,5).toLong()
    private val tooltipTimer = object : CountDownTimer(timerSecond*timerInterval,timerInterval){
        override fun onTick(p0: Long) {}

        override fun onFinish() {
            setTooltipVisibility()
        }

    }
    init {
        setTooltipVisibility()
        tooltipTimer.start()
    }

    override fun setTooltipVisibility() {
        super.setTooltipVisibility()
        tooltipTimer.cancel()
    }

}