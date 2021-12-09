package com.example.androidtooltip

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.VectorDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

class ToolTipView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null) :
    FrameLayout(context, attributeSet) {
    private val attr =
        context.theme.obtainStyledAttributes(attributeSet, R.styleable.ToolTipView, 0, 0)
    private val tooltipViewText = attr.getString(R.styleable.ToolTipView_tooltipText)
    private val tooltipTextSize =
        attr.getDimension(R.styleable.ToolTipView_tooltipTextSize, 0f)
    private val tooltipTextColor = attr.getColor(R.styleable.ToolTipView_tooltipTextColor,0x000000)
    private val direction = attr.getInt(R.styleable.ToolTipView_direction, 0).let {
        when(it){
            0 -> Direction.TOP
            1 -> Direction.BOTTOM
            2 -> Direction.LEFT
            else -> Direction.RIGHT
        }
    }
    private val tooltipHeadRatio =
        attr.getFloat(R.styleable.ToolTipView_tooltipHeadRatio, 0f)
    private val tooltipPadding = attr.getDimensionPixelSize(R.styleable.ToolTipView_tooltipPadding,0)
    private val tooltipColor = attr.getColor(R.styleable.ToolTipView_tooltipColor,0x000000)
    private val tooltipDrawable = (ContextCompat.getDrawable(context,R.drawable.tooltip_background) as GradientDrawable).apply {
        setColor(tooltipColor)
    }
    private val tooltipHeadDrawable = (ContextCompat.getDrawable(context,R.drawable.ic_tooltip_head) as VectorDrawable).apply {
        setTint(tooltipColor)
    }
    private val tooltipHeadWidth = attr.getDimensionPixelSize(R.styleable.ToolTipView_tooltipHeadWidth,9.toDp)
    private val tooltipHeadHeight = attr.getDimensionPixelSize(R.styleable.ToolTipView_tooltipHeadHeight,6.toDp)

    private val tooltipView = FrameLayout(context).apply {
        visibility = GONE
        setOnClickListener {
            setTooltipVisibility()
        }
    }.also(::addView)
    private val tooltipTextView = makeToolTipView()
    private val tooltipHeadView = makeTooltipHeadView()

    private var isVisible = false

    private val onGlobalLayoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            tooltipView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            setTooltipHeadRatio()
            setTooltipHeadPosition()
            setRotationTooltipHeadView()
            setMarginTooltipTextView()
        }
    }

    private fun setTooltipHeadRatio(){
        val maxPosition = when(direction){
            Direction.TOP,Direction.BOTTOM -> tooltipTextView.width
            Direction.RIGHT,Direction.LEFT -> tooltipTextView.height
        }
        val tooltipHeadPosition = maxPosition * tooltipHeadRatio - when(direction){
            Direction.TOP,Direction.BOTTOM -> tooltipHeadView.width/2
            Direction.RIGHT,Direction.LEFT -> tooltipHeadView.height/2
        }
        when(direction){
            Direction.TOP,Direction.BOTTOM -> tooltipHeadView.x = tooltipHeadPosition
            Direction.RIGHT,Direction.LEFT -> tooltipHeadView.y = tooltipHeadPosition
        }
    }

    private fun setRotationTooltipHeadView() {
        val rotation = when (direction) {
            Direction.BOTTOM -> 180.0f
            Direction.LEFT -> -90.0f
            Direction.RIGHT -> 90.0f
            Direction.TOP -> 0.0f
        }
        tooltipHeadView.rotation = rotation
    }

    private fun setTooltipHeadPosition(){
        when(direction){
            Direction.BOTTOM -> tooltipHeadView.y = tooltipTextView.height.toFloat()
            Direction.LEFT -> tooltipHeadView.x = 0f
            Direction.RIGHT -> tooltipHeadView.x = tooltipTextView.width.toFloat() - tooltipHeadView.height/2
            Direction.TOP -> tooltipHeadView.y = 0f
        }
    }

    private fun setMarginTooltipTextView(){
        val tooltipLayoutParams = tooltipTextView.layoutParams as LayoutParams
        val margin = tooltipHeadView.height
        when(direction){
            Direction.BOTTOM -> tooltipLayoutParams.bottomMargin = margin
            Direction.LEFT -> tooltipLayoutParams.leftMargin = margin
            Direction.RIGHT -> tooltipLayoutParams.rightMargin = margin
            Direction.TOP -> tooltipLayoutParams.topMargin = margin
        }
        tooltipTextView.requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun makeToolTipView(): TextView {
        return TextView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
            text = tooltipViewText
            setTextColor(tooltipTextColor)
            setPadding(tooltipPadding,tooltipPadding,tooltipPadding,tooltipPadding)
            setTextSize(TypedValue.COMPLEX_UNIT_PX, tooltipTextSize)
            background = tooltipDrawable
        }.also(tooltipView::addView)
    }

    private fun makeTooltipHeadView(): ImageView {
        return ImageView(context).apply {
            layoutParams = LayoutParams(
                tooltipHeadWidth,
                tooltipHeadHeight
            )
            background = tooltipHeadDrawable
        }.also(tooltipView::addView)
    }

    fun setTooltipText(text: String) {
        tooltipTextView.text = text
    }

    fun setTooltipVisibility() {
        tooltipView.visibility = if (isVisible) View.GONE else View.VISIBLE
        isVisible = !isVisible
    }

    enum class Direction{
        TOP,BOTTOM,LEFT,RIGHT
    }

}