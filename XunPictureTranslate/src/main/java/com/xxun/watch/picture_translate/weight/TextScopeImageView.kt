package com.xxun.watch.picture_translate.weight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import com.xxun.watch.picture_translate.R

class TextScopeImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    private val pathPaint: Paint = Paint()
    private var textScopeData: List<Rect>? = null

    init {
        pathPaint.color = resources.getColor(R.color.blue_5896FF)
        pathPaint.strokeWidth = 2F
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        /*val width = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)*/
        if (drawable == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }else{
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height = width*drawable.intrinsicHeight/drawable.intrinsicWidth
            val scale = width / drawable.intrinsicWidth
            textScopeData?.forEach {
                it.left = it.left*scale
                it.top = it.top*scale
                it.right = it.right*scale
                it.bottom = it.bottom*scale
            }
            setMeasuredDimension(width,height)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    fun setTextScope(list: List<Rect>) {
        textScopeData = list
    }
}