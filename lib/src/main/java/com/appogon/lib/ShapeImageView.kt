package com.appogon.lib

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.use

class ShapeImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatImageView(context, attrs, defStyleAttr) {

    private lateinit var shapePath: String

    private var src: Bitmap

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.ShapeImageView, 0, 0)
            .use {
                shapePath = it.getString(R.styleable.ShapeImageView_shapePath)
                    ?: throw IllegalArgumentException("shapePath attribute must not be empty")
            }
        src = (drawable as BitmapDrawable).bitmap
        build()
    }

    fun setupShapePath(value: String) {
        if (value.isEmpty()) throw IllegalArgumentException("shapePath attribute must not be empty")
        shapePath = value
        build()
    }

    fun setupSrc(value: Bitmap) {
        src = value
        build()
    }

    private fun build() {
        setImageBitmap(src.convertToShape(shapePath))
    }
}