package lv.zesloka.skani.ui.widgets.text

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import lv.zesloka.skani.R
import lv.zesloka.skani.databinding.WidgetTextInputFieldBinding

class TextInputField(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private val binding = WidgetTextInputFieldBinding.inflate(LayoutInflater.from(context), this)
    private var onTextChanged: OnTextChanged = {}

    init {
        binding.input.setOnFocusChangeListener { v, hasFocus ->
            binding.description.setTextColor(
                resources.getColor(
                    if (hasFocus) R.color.textInputFocused else R.color.textInput
                )
            )
        }

        binding.input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                onTextChanged(text.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })



        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.TextInputField,
            0, 0
        ).apply {
            try {
                if (hasValue(R.styleable.TextInputField_fieldDescription)) {
                    val description = getString(R.styleable.TextInputField_fieldDescription)
                    binding.description.text = description
                }

                val appearance = getInt(R.styleable.TextInputField_appearance, 0)
                if (appearance == Appearance.BOX) {
                    binding.input.setBackgroundResource(R.drawable.selector_input_field_box)
                    binding.input.textAlignment = View.TEXT_ALIGNMENT_CENTER
                }

                val inputType = getInt(R.styleable.TextInputField_inputType, 0)
                if (inputType == TextInputType.PASSWORD) {
                    binding.input.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                }
            } finally {
                recycle()
            }
        }
    }

    fun getInput(): String = binding.input.text.toString()

    fun setOnTextChangedListener(listener: OnTextChanged) {
        onTextChanged = listener
    }

    fun setError(error: String) {
        binding.error.text = error
        binding.error.visibility = View.VISIBLE
    }

    fun clearError() {
        binding.error.visibility = View.VISIBLE
    }

}

private interface Appearance {
    companion object {
        const val LINE = 0
        const val BOX = 1
    }
}

private interface TextInputType {
    companion object {
        const val TEXT = 0
        const val PASSWORD = 1
    }
}

typealias OnTextChanged = (text: String) -> Unit


