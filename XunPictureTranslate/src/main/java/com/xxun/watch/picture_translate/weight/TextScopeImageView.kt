package com.xxun.watch.picture_translate.weight

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.xxun.watch.picture_translate.R

class TextScopeImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    private val pathPaint: Paint = Paint()
    private var pathList = mutableListOf<Path>()

    init {
        pathPaint.color = resources.getColor(R.color.blue_6996ff)
        pathPaint.strokeWidth = 2F
        pathPaint.style = Paint.Style.STROKE
        pathPaint.pathEffect = CornerPathEffect(4F)
        pathPaint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (drawable == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        } else {
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height = (width * drawable.intrinsicHeight * 1F / drawable.intrinsicWidth).toInt()
            setMeasuredDimension(width, height)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        pathList.forEach {
            canvas?.drawPath(it, pathPaint)
        }
    }

    fun setTextScopeData(allRectTextDataList: List<List<Pair<Int, Int>>>?) {
        pathList.clear()
        val scale = width * 1F / drawable.intrinsicWidth
        allRectTextDataList?.forEach {
            val path = Path()
            path.moveTo(it[0].first * scale, it[0].second * scale)
            path.lineTo(it[1].first * scale, it[1].second * scale)
            path.lineTo(it[2].first * scale, it[2].second * scale)
            path.lineTo(it[3].first * scale, it[3].second * scale)
            path.close()
            pathList.add(path)
        }
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            listener?.let {
                val rawX = event.x
                val rawY = event.y
                pathList.forEachIndexed { index, path ->
                    if (isInPath(path, rawX, rawY)) {
                        it(index)
                        return@forEachIndexed
                    }
                    Log.d("NKW", "forEachIndexed")
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun isInPath(path: Path, x: Float, y: Float): Boolean {
        val rectF = RectF()
        path.computeBounds(rectF, true)
        val region = Region()
        region.setPath(
            path,
            Region(rectF.left.toInt(), rectF.top.toInt(), rectF.right.toInt(), rectF.bottom.toInt())
        )
        return region.contains(x.toInt(), y.toInt())
    }

    private var listener: ((Int) -> Unit)? = null
    fun setOnClickTextScopeListener(listener: (Int) -> Unit) {
        this@TextScopeImageView.listener = listener
    }
}