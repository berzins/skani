package lv.zesloka.skani.ui.base

interface BackNavigationListener {
    /**
     * Return true if the intentions is to continue on back flow
     * Check Main activity for more details.
     */
    fun onBack(): Boolean
}