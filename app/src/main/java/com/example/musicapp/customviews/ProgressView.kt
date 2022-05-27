package com.example.musicapp.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.musicapp.R
import com.example.musicapp.Utils

class ProgressView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var baseColor = Color.DKGRAY
        set(value) {
            field = value
            invalidate()
        }
    var primaryColor = Color.LTGRAY
        set(value) {
            field = value
            invalidate()
        }

    var thickness = 1
        set(value) {
            field = value
            invalidate()
        }

    var value = 5F
        set(value) {
            field = value
            invalidate()
        }

    var maxValue = 10F
        set(value) {
            field = value
            invalidate()
        }

    private val oval = RectF(20F, 20F, 200F, 200F)

    init {

        val attributeArray =
            context.theme.obtainStyledAttributes(attributeSet, R.styleable.ProgressView, 0, 0)

        baseColor = attributeArray.getColor(R.styleable.ProgressView_base_color, Color.DKGRAY)
        primaryColor = attributeArray.getColor(R.styleable.ProgressView_primary_color, Color.LTGRAY)
        value = attributeArray.getInteger(R.styleable.ProgressView_value, 5).toFloat()
        maxValue = attributeArray.getInteger(R.styleable.ProgressView_max_value, 10).toFloat()
        thickness = attributeArray.getInteger(R.styleable.ProgressView_thick, 1)

        //paint.color = startColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10F * thickness

        attributeArray.recycle()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val desiredHeight = Utils.dpToPx(300, context)
        val desiredWidth = desiredHeight
        var newWidth = 0
        var newHeight = 0

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        when (widthMode) {

            MeasureSpec.AT_MOST -> {
                newWidth = desiredHeight.coerceAtMost(widthSize)
            }
            MeasureSpec.EXACTLY -> {
                newWidth = widthSize
            }
            MeasureSpec.UNSPECIFIED -> {
                newWidth = desiredWidth
            }

        }

        when (heightMode) {

            MeasureSpec.AT_MOST -> {
                newHeight = desiredHeight.coerceAtMost(heightSize)
            }
            MeasureSpec.EXACTLY -> {
                newHeight = heightSize
            }
            MeasureSpec.UNSPECIFIED -> {
                newHeight = desiredHeight
            }

        }

        setMeasuredDimension(newWidth, newHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.shader = LinearGradient(0F, 0F, 0F, height.toFloat(), baseColor, primaryColor, Shader.TileMode.MIRROR)

        oval.top = 0F + paddingTop
        oval.left = 0F + paddingLeft
        oval.bottom = height.toFloat() - paddingBottom
        oval.right = width.toFloat() - paddingRight

        val centerX = (width / 2).toFloat()
        val centerY = (height / 2).toFloat()


        canvas?.let {
            //it.drawCircle(centerX, centerY, (width / 2).toFloat(), paint)
            it.drawArc(oval, 270F, ((value / maxValue) * 360) * -1, false, paint)
        }
    }

}