package pl.edu.zut.mad.schedule.ui

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView

class VerticalTextView(context: Context, attrs: AttributeSet) : TextView(context, attrs) {

    private val topDown: Boolean =
        if (Gravity.isVertical(gravity) && (gravity and Gravity.VERTICAL_GRAVITY_MASK) == Gravity.BOTTOM) {
            gravity = gravity and Gravity.HORIZONTAL_GRAVITY_MASK or Gravity.TOP
            false
        } else
            true

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec)
        setMeasuredDimension(measuredHeight, measuredWidth)
    }

    override fun onDraw(canvas: Canvas) {
        val textPaint = paint
        textPaint.color = currentTextColor
        textPaint.drawableState = drawableState

        canvas.save()

        if (topDown) {
            canvas.translate(width.toFloat(), 0F)
            canvas.rotate(90F)
        } else {
            canvas.translate(0F, height.toFloat())
            canvas.rotate(-90F)
        }

        canvas.translate(compoundPaddingLeft.toFloat(), extendedPaddingTop.toFloat())
        layout.draw(canvas)
        canvas.restore()
    }
}
