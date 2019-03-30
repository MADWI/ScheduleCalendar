package pl.edu.zut.mad.schedule.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import pl.edu.zut.mad.schedule.R

class CleanableTextInput : AppCompatAutoCompleteTextView,
    View.OnTouchListener, View.OnFocusChangeListener, TextWatcher {

    companion object {
        private val CLEAR_ICON_ID = R.drawable.ic_clear_white_24dp
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
        super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private lateinit var clearIcon: Drawable
    private var alwaysShowClearIcon: Boolean = false
    private val widthMinusIcon: Int by lazy { width - paddingRight - clearIcon.intrinsicWidth }

    private fun init(attrs: AttributeSet) {
        initAttributes(attrs)
        initClearIcon()
        initListeners()
    }

    private fun initAttributes(attrs: AttributeSet) {
        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.CleanableTextInput, 0, 0)
        alwaysShowClearIcon = attributes.getBoolean(R.styleable.CleanableTextInput_alwaysShowClearIcon, false)
        attributes.recycle()
    }

    private fun initClearIcon() {
        val icon = ContextCompat.getDrawable(context, CLEAR_ICON_ID) ?: return
        val wrappedIcon = DrawableCompat.wrap(icon)
        DrawableCompat.setTint(wrappedIcon, currentHintTextColor)
        clearIcon = wrappedIcon
        clearIcon.setBounds(0, 0, clearIcon.intrinsicHeight, clearIcon.intrinsicHeight)
        setClearIconVisibility(false)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() {
        super.setOnTouchListener(this)
        super.setOnFocusChangeListener(this)
        addTextChangedListener(this)
    }

    private fun setClearIconVisibility(isVisible: Boolean) {
        clearIcon.setVisible(isVisible, false)
        val endDrawable = if (isVisible) clearIcon else null
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1],
            endDrawable, compoundDrawables[3])
    }

    override fun onTouch(view: View?, event: MotionEvent): Boolean {
        val consumeIconTouch = clearIcon.isVisible && isOnIconTouch(event) && isActionUp(event)
        if (consumeIconTouch) {
            setText("")
        }
        return consumeIconTouch
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (hasFocus) {
            setClearIconVisibility(text.isNotEmpty())
        } else {
            setClearIconVisibility(false)
        }
    }

    override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
        if (alwaysShowClearIcon || isFocused) {
            setClearIconVisibility(text.isNotEmpty())
        }
    }

    override fun afterTextChanged(editable: Editable?) {
    }

    override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
    }

    private fun isActionUp(event: MotionEvent) = event.action == MotionEvent.ACTION_UP

    private fun isOnIconTouch(event: MotionEvent) = event.x > widthMinusIcon
}
