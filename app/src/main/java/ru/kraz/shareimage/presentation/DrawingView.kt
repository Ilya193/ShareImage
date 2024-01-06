package ru.kraz.shareimage.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class DrawingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var path = Path()

    private var bitmap: Bitmap? = null
    private var canvas: Canvas? = null

    private var paint = Paint().apply {
        color = Color.GREEN
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 12f
    }

    private val paths = mutableListOf<Path>()
    private val colors = mutableListOf<Int>()
    private var color = Color.GREEN

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (index in 0 until paths.size) {
            paint.color = colors[index]
            canvas.drawPath(paths[index], paint)
        }
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y)
                colors.add(color)
                paths.add(Path(path))
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
            }
            MotionEvent.ACTION_UP -> {
                colors.add(color)
                paths.add(Path(path))
                path.reset()
            }
        }

        invalidate()
        return true
    }

    fun save(block: (File) -> Unit) {
        if (paths.isNotEmpty()) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            canvas = Canvas(bitmap!!)
            draw(canvas!!)
            val file = File(context.cacheDir, "${UUID.randomUUID()}.png")
            val stream = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.close()
            block(file)
        }
    }

    fun clear() {
        if (paths.isNotEmpty()) {
            path.reset()
            paths.clear()
            colors.clear()
            invalidate()
        }
    }

    fun undo() {
        if (paths.isNotEmpty()) {
            paths.removeLast()
            colors.removeLast()
            invalidate()
        }
    }

    fun setColor(colorHex: String) {
        color = Color.parseColor(colorHex)
    }
}