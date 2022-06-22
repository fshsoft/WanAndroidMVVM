package com.fsh.android.wanandroidmvvm.custom.loadingview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.fsh.android.wanandroidmvvm.common.utils.ColorUtil

/**
 * Created with Android Studio.
 * Description:
 */
class ShapeView(context: Context?, attrs: AttributeSet? = null) :
    View(context, attrs, 0) {
    private var mPaint: Paint

    private var mCurrentShape = Shapes.Circle

    init {
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(
            if (width > height) height else width,
            if (width > height) height else width
        )
    }

    override fun onDraw(canvas: Canvas?) {
        when (mCurrentShape) {
            Shapes.Circle -> {
                // 取屏幕中心位置
                var center: Float = (height / 2).toFloat()
                // 设置画笔颜色为主题颜色
                mPaint.setColor(ColorUtil.getColor(context))
                // 在布局中心绘制
                canvas!!.drawCircle(center, center, center, mPaint)
            }

            Shapes.Square -> {
                mPaint.setColor(ColorUtil.getColor(context))
                canvas!!.drawRect(0.0f, 0.0f, width.toFloat(), height.toFloat(), mPaint)
            }

            Shapes.Triangle -> {
                mPaint.setColor(ColorUtil.getColor(context))
                var path = Path()
                var dy = (Math.sqrt(3.0) * height / 2).toInt()
                var offsetY = (height - dy) / 2
                path.moveTo((width / 2).toFloat(), offsetY.toFloat())
                path.lineTo(width.toFloat(), (height - offsetY).toFloat())
                path.lineTo(0.0f, (height - offsetY).toFloat())
                path.close()
                canvas!!.drawPath(path, mPaint)
            }
        }
    }

    fun changeShape() {
        if (mCurrentShape == Shapes.Circle) {
            mCurrentShape = Shapes.Square
        } else if (mCurrentShape == Shapes.Square) {
            mCurrentShape = Shapes.Triangle
        } else if (mCurrentShape == Shapes.Triangle) {
            mCurrentShape = Shapes.Circle
        }
        invalidate()
    }
}