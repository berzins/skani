package lv.zesloka.domain.model.auth.signup

import lv.zesloka.domain.model.auth.AuthActoinDeliveryType

data class AuthNextSignUpStep(val action: NextSignUpAction, val deliveryType: AuthActoinDeliveryType)