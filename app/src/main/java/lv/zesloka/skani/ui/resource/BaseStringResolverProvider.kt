package lv.zesloka.skani.ui.resource

import android.app.Application
import lv.zesloka.skani.R
import lv.zesloka.skani.presentation.model.text.AppStringId
import lv.zesloka.skani.presentation.model.text.AppStringId.*
import lv.zesloka.skani.presentation.vm.contract.Resolver
import lv.zesloka.skani.presentation.vm.contract.ResolverProvider
import java.lang.IllegalArgumentException
import javax.inject.Inject

open class BaseStringResolverProvider(private val resolvers: Map<AppStringId, Resolver<String>>) :
    ResolverProvider<String> {
    override fun get(id: AppStringId): Resolver<String> {
        return resolvers[id] ?: throw IllegalArgumentException("Can't provide resolver with id $id")
    }
}

class AppStrings @Inject constructor(
    val app: Application
) : BaseStringResolverProvider(
    mapOf(
        ERR_MSG_USERNAME_TOO_SHORT to ResourceStrResolver(
            app,
            R.string.err_msg_input_username_too_short
        ),
        ERR_MSG_USERNAME_TOO_LONG to ResourceStrResolver(
            app,
            R.string.err_msg_input_username_too_long
        ),
        ERR_MSG_EMAIL_INVALID to ResourceStrResolver(app, R.string.err_msg_input_email_invalid),
        ERR_MSG_PASSWORD_EMPTY to ResourceStrResolver(app, R.string.err_msg_input_password_empty),
        ERR_MSG_UNKNOWN_ERROR to ResourceStrResolver(app, R.string.err_msg_unknown),
        TITLE_SIGN_UP to ResourceStrResolver(app, R.string.title_sign_up),
        ERR_MSG_INVALID_SIGN_UP_INPUT to ResourceStrResolver(
            app,
            R.string.err_msg_invalid_sign_up_input
        ),
        LABEL_ACTION_POSITIVE to ResourceStrResolver(app, R.string.positive_action_label),
        TITLE_LOGIN to ResourceStrResolver(app, R.string.title_login),
        ERR_MSG_WRONG_USERNAME_OR_PASSWORD to ResourceStrResolver(
            app,
            R.string.msg_wrong_user_password
        ),
        TEXT_INITIALIZE to ResourceStrResolver(app, R.string.txt_initialize),
        TEXT_USER_LODGED_IN to ResourceStrResolver(app, R.string.user_logged_in),
        TEXT_USER_NOT_LODGED_IN to ResourceStrResolver(app, R.string.user_not_logged_in),
        ERR_MSG_ERROR_HAPPENED to ResourceStrResolver(app, R.string.err_msg_error_happened),
        ERROR to ResourceStrResolver(app, R.string.error),
        GENERIC_ERROR to ResourceStrResolver(app, R.string.generic_error_message),
        ERR_MSG_INVALID_EMAIL_VERIFY_CODE to ResourceStrResolver(
            app,
            R.string.err_msg_invalid_email_verify_code
        )
    )
)