package lv.zesloka.domain.contract

interface Dictionary<Out> {
    fun get(): Out
}