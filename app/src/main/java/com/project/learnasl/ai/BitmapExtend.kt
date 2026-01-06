package com.project.learnasl.ai

import android.graphics.Bitmap
import androidx.core.graphics.scale

fun Bitmap.rotate(degrees: Int): Bitmap {
    if (degrees == 0) return this

    val matrix = android.graphics.Matrix().apply {
        postRotate(degrees.toFloat())
    }

    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun Bitmap.resize(width: Int, height: Int): Bitmap {
    return this.scale(width, height)
}
