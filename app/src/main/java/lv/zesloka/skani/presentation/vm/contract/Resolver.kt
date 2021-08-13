package lv.zesloka.skani.presentation.vm.contract

interface Resolver<Out> {
    fun get(): Out
}