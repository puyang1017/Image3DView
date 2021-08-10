package com.example.android3dimage

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView


class Image3DView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {
    private lateinit var prospectImage: GyroscopeImageView//前景
    private lateinit var middleImage: GyroscopeImageView//中景
    private lateinit var backgroundImage: GyroscopeImageView//背景

    init {

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.Image3DView)
            val prospect = a.getDrawable(R.styleable.Image3DView_prospectImage)
            val middle = a.getDrawable(R.styleable.Image3DView_middleImage)
            val background = a.getDrawable(R.styleable.Image3DView_backgroundImage)
            a.recycle()
            prospectImage = GyroscopeImageView(context)
            prospectImage.flipFlag = true
            middleImage = GyroscopeImageView(context)
            backgroundImage = GyroscopeImageView(context)
            prospectImage.setImageDrawable(prospect)
            middleImage.setImageDrawable(middle)
            backgroundImage.setImageDrawable(background)
            prospectImage.scaleType = ImageView.ScaleType.CENTER
            middleImage.scaleType = ImageView.ScaleType.CENTER
            backgroundImage.scaleType = ImageView.ScaleType.CENTER
            addView(backgroundImage)
            addView(middleImage)
            addView(prospectImage)
            scale(prospectImage, 1.6f)
            scale(backgroundImage, 1.6f)
            GyroscopeObserver.getInstance().register(context)
        }
    }

    private fun scale(view: ImageView, max: Float) {
        ObjectAnimator.ofFloat(view, "scaleY", 1f, max).setDuration(0).start()
        ObjectAnimator.ofFloat(view, "scaleX", 1f, max).setDuration(0).start()
    }
}