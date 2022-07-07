package com.project.helpmeat.components

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.project.helpmeat.R


class NonePaddingTextView : AppCompatTextView {
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, R.style.NonePaddingTextViewTheme)

    constructor(context: Context, attrs: AttributeSet?)
            : super(context, attrs, R.style.NonePaddingTextViewTheme)
}