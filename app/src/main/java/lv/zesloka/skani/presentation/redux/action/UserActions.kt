package lv.zesloka.skani.presentation.redux.action

import lv.zesloka.domain.model.auth.AuthSignIn
import lv.zesloka.domain.model.auth.AuthSignUp

sealed class UserActions {
    class UserStateFetchStart
    data class UserStateSuccess(val isSignedIn: Boolean)
    data class UserStateError(val code: Int, val message: String)
    class GetUserStatus

    sealed class Register {
        sealed class SignUp {
            data class Start(val username: String, val email: String, val password: String)
            data class Success(val signUp: AuthSignUp)
            data class Error(val someData: String)
        }

        sealed class VerifyEmail {
            data class Start(val username: String, val code: String)
            data class Success(val signUp: AuthSignUp)
            data class Error(val someData: String)
        }

        class Complete
    }

    sealed class SignIn {
        data class Start(val username: String, val password: String)
        data class Success(val signIn: AuthSignIn)
        data class Error(val someData: String)
    }

    sealed class SignOut {
        class Start
        class Success
        data class Error(val someData: String)
    }
}