package lv.zesloka.domain.model.auth.signin

import lv.zesloka.domain.model.auth.AuthActoinDeliveryType

data class AuthSignInNextStep(
    val nextSignInAction: AuthNextSignInAction,
    val actionDeliveryType: AuthActoinDeliveryType
) {
    companion object
}