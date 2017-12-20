package pl.edu.zut.mad.schedule.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.design.widget.TextInputEditText
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import pl.edu.zut.mad.schedule.R

class CleanableTextInput : TextInputEditText,
    View.OnTouchListener, View.OnFocusChangeListener, TextWatcher {

    companion object {
        private val CLEAR_ICON_ID = R.drawable.abc_ic_clear_material
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

    @SuppressLint("ClickableViewAccessibility")
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
        val icon = ContextCompat.getDrawable(context, CLEAR_ICON_ID)
        val wrappedIcon = DrawableCompat.wrap(icon)
        DrawableCompat.setTint(wrappedIcon, highlightColor)
        clearIcon = wrappedIcon
        clearIcon.setBounds(0, 0, clearIcon.intrinsicHeight, clearIcon.intrinsicHeight)
        setClearIconVisibility(false)
    }

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
        if (clearIcon.isVisible && isOnIconTouch(event) && isActionUp(event)) {
            setText("")
            return true
        }
        return false
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (hasFocus) {
            setClearIconVisibility(text.isNotEmpty())
        } else {
            setClearIconVisibility(false)
        }
    }

    override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
        if (alwaysShowClearIcon || isFocused ) {
            setClearIconVisibility(text.isNotEmpty())
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    private fun isActionUp(event: MotionEvent) = event.action == MotionEvent.ACTION_UP

    private fun isOnIconTouch(event: MotionEvent) = event.x > widthMinusIcon
}
