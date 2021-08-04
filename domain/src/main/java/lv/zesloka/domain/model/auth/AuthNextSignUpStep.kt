package lv.zesloka.domain.model.auth

data class AuthNextSignUpStep(val action: NextSignUpAction, val deliveryType: AuthActoinDeliveryType)