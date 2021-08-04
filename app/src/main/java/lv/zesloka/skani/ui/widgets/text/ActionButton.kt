package lv.zesloka.skani.ui.widgets.text

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.DrawableCompat
import lv.zesloka.skani.R

class ActionButton(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    companion object {
        private const val INT_NOT_SET = -1
    }

    private val title: TextView
    private val leftIcon: ImageView
    private val container: ConstraintLayout

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.widget_action_button, this, true)
        container = root.findViewById(R.id.container)
        title = root.findViewById(R.id.title)
        leftIcon = root.findViewById(R.id.left_icon)


        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ActionButton,
            0, 0
        ).apply {
            try {
                val title = getString(R.styleable.ActionButton_title)
                this@ActionButton.title.text = title
                val leftIconId = getResourceId(R.styleable.ActionButton_leftIcon, INT_NOT_SET)
                if (leftIconId != INT_NOT_SET) {
                    leftIcon.setImageResource(leftIconId)
                    leftIcon.visibility = View.VISIBLE
                }
            } finally {
                recycle()
            }
        }
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        updateIconTint()
    }

    private fun updateIconTint() {
        val colours = resources.getColorStateList(R.color.selector_primary_action_button_icon_tint)
        val drawable = DrawableCompat.wrap(leftIcon.drawable)
        DrawableCompat.setTintList(drawable, colours)
        leftIcon.setImageDrawable(drawable)

    }


}