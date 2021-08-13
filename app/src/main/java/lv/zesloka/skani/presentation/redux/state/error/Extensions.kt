package lv.zesloka.skani.presentation.redux.state.error

import lv.zesloka.domain.usecase.base.ErrorCode

fun RdxError.Companion.initial() =
    RdxError(0, ErrorCode.NONE)

fun RdxError.new(code: Int) =
    this.copy(version = this.version + 1, code = code)