package com.example.android3dimage

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet

class GyroscopeImageView : androidx.appcompat.widget.AppCompatImageView, GyroscopeImpl {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        init()
    }

    var mWidth = 0
    var mHeight = 0
    var mDrawableWidth = 0
    var mDrawableHeight = 0

    // 图片最大偏移量
    private var mMaxOffsetX = 0f
    private var mMaxOffsetY = 0f

    // 相对于初始位置的旋转弧度
    @JvmField
    var mRotateRadianX = 0f

    @JvmField
    var mRotateRadianY = 0f

    private var progressX = 0f
    private var progressY = 0f

    @JvmField
    var flipFlag = false

    private fun init() {
        scaleType = ScaleType.CENTER
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (drawable != null) {
            mWidth = MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight
            mHeight = MeasureSpec.getSize(heightMeasureSpec) - paddingTop - paddingBottom

            mDrawableWidth = drawable.intrinsicWidth
            mDrawableHeight = drawable.intrinsicHeight

            mMaxOffsetX = Math.abs((mDrawableWidth - mWidth) * 0.2f)
            mMaxOffsetY = Math.abs((mDrawableHeight - mHeight) * 0.2f)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        if (drawable == null || isInEditMode) {
            super.onDraw(canvas)
            return
        }
        val currentOffsetX = mMaxOffsetX * progressX
        val currentOffsetY = mMaxOffsetY * progressY
        canvas?.save()
        canvas?.translate(currentOffsetX, currentOffsetY)
        super.onDraw(canvas)
        canvas?.restore()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        GyroscopeObserver.getInstance().addGyroscopeImageViews(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        GyroscopeObserver.getInstance().removeGyroscopeImageViews(this)
    }

    override fun updateProgress(progressX: Float, progressY: Float) {
        if (flipFlag) {
            this.progressX = -progressX
            this.progressY = -progressY
        } else {
            this.progressX = progressX
            this.progressY = progressY
        }
        invalidate()
    }
}