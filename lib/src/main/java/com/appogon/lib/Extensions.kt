package com.appogon.lib

import android.graphics.*
import androidx.core.graphics.PathParser

fun Bitmap.convertToShape(shapePath: String) = getCroppedBitmap(getShapePath(shapePath))

fun Bitmap.getCroppedBitmap(path: Path): Bitmap {
    val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    paint.color = -0x1000000
    canvas.drawPath(path, paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(this, 0f, 0f, paint)
    return output
}

private fun Bitmap.getShapePath(shapePath: String) = PathParser.createPathFromPathData(shapePath).resizePath(
    width.toFloat(),
    height.toFloat()
)

private fun Path.resizePath(width: Float, height: Float): Path {
    val bounds = RectF(0f, 0f, width, height)
    val resizedPath = Path(this)
    val src = RectF()
    resizedPath.computeBounds(src, true)
    val resizeMatrix = Matrix()
    resizeMatrix.setRectToRect(src, bounds, Matrix.ScaleToFit.FILL)
    resizedPath.transform(resizeMatrix)
    return resizedPath
}