package lv.zesloka.skani.ui.widgets.navbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import lv.zesloka.skani.databinding.WidgetNavigationBarBinding

typealias ActionListener = () -> Unit

class NavigationBar(context: Context, attrs: AttributeSet?) :
    FrameLayout(context, attrs) {

    private val binding = WidgetNavigationBarBinding.inflate(LayoutInflater.from(context), this)


    fun setTitle(title: String) {
        binding.title.text = title
    }

    fun setRightActionListener(listener: ActionListener) {
        binding.rightAction.setOnClickListener { listener() }
    }

    fun setLeftActionListener(listener: ActionListener) {
        binding.leftAction.setOnClickListener { listener() }
    }

    fun setRightActionIcon(resId: Int) {
        binding.rightAction.setImageResource(resId)
    }

    fun setLeftActionIcon(resId: Int) {
        binding.leftAction.setImageResource(resId)
    }

    fun showRightAction(isVisible: Boolean) {
        binding.rightAction.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    fun showLeftAction(isVisible: Boolean) {
        binding.leftAction.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }


}