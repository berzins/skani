package lv.zesloka.skani.presentation.model.dialog

import lv.zesloka.skani.presentation.model.text.AppStringId
import lv.zesloka.skani.presentation.vm.contract.ResolverProvider

data class SingleActionDialog(
    val title: String,
    val message: String,
    val actionLabel: String,
    val action: () -> Unit = {}
) {
    companion object
}

fun SingleActionDialog.Companion.genericError(
    strings: ResolverProvider<String>,
    action: () -> Unit
) = SingleActionDialog(
    title = strings.get(AppStringId.ERROR).get(),
    message = strings.get(AppStringId.GENERIC_ERROR).get(),
    actionLabel = strings.get(AppStringId.LABEL_ACTION_POSITIVE).get(),
    action = action
)


