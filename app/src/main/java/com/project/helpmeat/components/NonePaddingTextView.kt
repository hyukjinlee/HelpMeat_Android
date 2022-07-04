package com.project.helpmeat.components

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView


class NonePaddingTextView : AppCompatTextView {
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet?)
            : super(context, attrs)

    init {
        includeFontPadding = false
    }

    private var mAdditionalPadding = 0.0f

    override fun onDraw(canvas: Canvas) {
        val yOff: Float = -mAdditionalPadding / 6
        canvas.translate(0.0f, yOff)
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var hms = heightMeasureSpec
        getAdditionalPadding()
        val mode = MeasureSpec.getMode(hms)
        if (mode != MeasureSpec.EXACTLY) {
            val measureHeight = measureHeight(text.toString(), widthMeasureSpec)
            var height: Int = (measureHeight - mAdditionalPadding).toInt()
            height += paddingTop + paddingBottom
            hms = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        }
        super.onMeasure(widthMeasureSpec, hms)
    }

    private fun measureHeight(text: String, widthMeasureSpec: Int): Int {
        val textView = TextView(context)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        textView.text = text
        textView.measure(widthMeasureSpec, 0)
        return textView.measuredHeight
    }

    private fun getAdditionalPadding(): Float {
        val textView = TextView(context)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        textView.setLines(1)
        textView.measure(0, 0)
        val measuredHeight = textView.measuredHeight
        if (measuredHeight - textSize > 0) {
            mAdditionalPadding = measuredHeight - textSize
        }
        return mAdditionalPadding
    }
}