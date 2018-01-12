package pl.edu.zut.mad.schedule.ui

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView

class VerticalTextView(context: Context, attrs: AttributeSet) : TextView(context, attrs) {

    companion object {
        private const val ROTATE_CLOCKWISE_90 = 90F
    }

    private val gravityIsVerticalAndBottom = Gravity.isVertical(gravity) && gravityIsBottom()

    init {
        if (gravityIsVerticalAndBottom) {
            gravity = gravity and Gravity.HORIZONTAL_GRAVITY_MASK or Gravity.TOP
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec)
        setMeasuredDimension(measuredHeight, measuredWidth)
    }

    override fun onDraw(canvas: Canvas) {
        val textPaint = paint
        textPaint.color = currentTextColor
        textPaint.drawableState = drawableState
        canvas.save()
        rotateCanvas(canvas)
        canvas.translate(compoundPaddingLeft.toFloat(), extendedPaddingTop.toFloat())
        layout.draw(canvas)
        canvas.restore()
    }

    private fun gravityIsBottom() =
        (gravity and Gravity.VERTICAL_GRAVITY_MASK) == Gravity.BOTTOM

    private fun rotateCanvas(canvas: Canvas) {
        if (gravityIsVerticalAndBottom) {
            canvas.translate(0F, height.toFloat())
            canvas.rotate(-ROTATE_CLOCKWISE_90)
        } else {
            canvas.translate(width.toFloat(), 0F)
            canvas.rotate(ROTATE_CLOCKWISE_90)
        }
    }
}
