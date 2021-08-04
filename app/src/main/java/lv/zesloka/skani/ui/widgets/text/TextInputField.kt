package lv.zesloka.skani.ui.widgets.text

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import lv.zesloka.skani.R
import lv.zesloka.skani.databinding.WidgetTextInputFieldBinding

class TextInputField(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private val binding = WidgetTextInputFieldBinding.inflate(LayoutInflater.from(context), this)

    init {
        binding.input.setOnFocusChangeListener { v, hasFocus ->
            binding.description.setTextColor(
                resources.getColor(
                    if (hasFocus) R.color.textInputFocused else R.color.textInput
                )
            )
        }

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

            } finally {
                recycle()
            }
        }
    }

    fun getInput(): String = binding.input.text.toString()

}

private interface Appearance {
    companion object {
        const val LINE = 0
        const val BOX = 1
    }
}