package lv.zesloka.skani.presentation.vm.contract

import lv.zesloka.skani.presentation.model.text.AppStringId

interface ResolverProvider<Out> {
    fun get(id: AppStringId): Resolver<Out>
}

