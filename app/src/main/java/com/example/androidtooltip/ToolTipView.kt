package com.example.androidtooltip

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

class ToolTipView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null) :
    FrameLayout(context, attributeSet) {
    private val attr =
        context.theme.obtainStyledAttributes(attributeSet, R.styleable.ToolTipView, 0, 0)
    private val toolTipText = attr.getString(R.styleable.ToolTipView_tooltipText)
    private val toolTipTextSize =
        attr.getDimension(R.styleable.ToolTipView_tooltipTextSize, 0f)
    private val direction = attr.getInt(R.styleable.ToolTipView_direction, 0).let {
        when(it){
            0 -> Direction.TOP
            1 -> Direction.BOTTOM
            2 -> Direction.LEFT
            else -> Direction.RIGHT
        }
    }
    private val triangleHorizontalRatio =
        attr.getFloat(R.styleable.ToolTipView_triangleHorizontalRatio, 0f)
    private val tooltipPadding = attr.getDimensionPixelSize(R.styleable.ToolTipView_tooltipPadding,0)

    private val tooltipView = FrameLayout(context).apply {
        visibility = GONE
    }
    private val tooltipTextView = makeToolTipView()
    private val tooltipHeadView = makeTooltipHeadView()

    private var isVisible = false

    private val toolTipOnGlobalLayoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            tooltipView.viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    }

    private val headerOnGlobalLayoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            tooltipHeadView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            rotationHeadView()
        }
    }

    init {
        addView(tooltipView)
    }

    /*private fun setDirectionTop() {
        val params = binding.tooltipText.layoutParams as LinearLayout.LayoutParams
        params.topMargin = binding.triangle.height
        binding.tooltipText.y = binding.triangle.height.toFloat()
        binding.triangle.x = getPositionXByRatio(triangleHorizontalRatio)
        binding.triangle.y = 0f
        binding.triangle.rotation = 0f
    }

    private fun setDirectionBottom() {
        binding.tooltipText.y = 0f
        binding.triangle.x = getPositionXByRatio(triangleHorizontalRatio)
        binding.triangle.y = binding.tooltipText.height.toFloat()
        binding.triangle.rotation = 180f
    }
*/
    private fun makeToolTipView(): TextView {
        return TextView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
            setPadding(tooltipPadding,tooltipPadding,tooltipPadding,tooltipPadding)
            setTextSize(TypedValue.COMPLEX_UNIT_PX, toolTipTextSize)
            setBackgroundResource(R.drawable.tooltip_background)
            text = "전일 대비 등락은 24시간 전의 시세와 비교하여\\n표시되며 해당 영역은 []의 시세 데이터를 반영해\\n1분 주기로 업데이트됩니다.(2021.11.04 01:11) "
        }.also(tooltipView::addView)
    }

    private fun makeTooltipHeadView(): ImageView {
        return ImageView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
            setBackgroundResource(R.drawable.ic_triangle)
        }.also(tooltipView::addView)
    }

    private fun rotationHeadView() {
        when (direction) {
            Direction.BOTTOM -> tooltipHeadView.rotation = 180.0f
            Direction.LEFT -> tooltipHeadView.rotation = -90.0f
            Direction.RIGHT -> tooltipHeadView.rotation = 90.0f
            else -> tooltipHeadView.rotation = 0.0f
        }
    }

    /*private fun getPositionXByRatio(ratio: Float): Float {
        val wholeWidth = binding.tooltipText.width
        return wholeWidth * ratio - binding.triangle.width / 2
    }*/

    fun setTooltipText(text: String) {
        tooltipTextView.text = text
    }

    fun setTooltipVisibility() {
        tooltipView.visibility = if (isVisible) View.GONE else View.VISIBLE
        isVisible = !isVisible
    }

    data class ToolTipCoordinate(
        val x: Float,
        val y: Float,
        val width: Int,
        val height: Int
    )

    data class HeaderCoordinate(
        val width: Int,
        val height: Int
    )

    enum class Direction{
        TOP,BOTTOM,LEFT,RIGHT
    }

}