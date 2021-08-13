package lv.zesloka.skani.presentation.redux.action

import lv.zesloka.domain.model.auth.signin.AuthSignIn
import lv.zesloka.domain.model.auth.signup.AuthSignUp

sealed class UserActions {
    class UserStateFetchStart
    data class UserStateSuccess(val isSignedIn: Boolean)
    data class UserStateError(val code: Int, val message: String)
    class GetUserStatus

    sealed class Register {
        sealed class SignUp {
            data class Start(val username: String, val email: String, val password: String)
            data class Success(val signUp: AuthSignUp)
            data class Error(val errorCode: Int)
        }

        sealed class VerifyEmail {
            data class Start(val username: String, val code: String)
            data class Success(val signUp: AuthSignUp)
            data class Error(val errorCode: Int)
        }

        class Complete

//        sealed class Validate {
//            sealed class Username {
//                data class Start(val username: String)
//                data class Success(val username: String)
//                data class Error(val errorCode: Int)
//            }
//
//            sealed class Email {
//                data class Start(val username: String)
//                data class Success(val username: String)
//                data class Error(val errorCode: Int)
//            }
//
//            sealed class Password {
//                data class Start(val username: String)
//                data class Success(val username: String)
//                data class Error(val errorCode: Int)
//            }
//        }
    }

    sealed class SignIn {
        data class Start(val username: String, val password: String)
        data class Success(val signIn: AuthSignIn)
        data class Error(val errorCode: Int)

//        sealed class Validate {
//            sealed class Username {
//                data class Start(val username: String)
//                data class Success(val username: String)
//                data class Error(val errorCode: Int)
//            }
//
//            sealed class Password {
//                data class Start(val username: String)
//                data class Success(val username: String)
//                data class Error(val errorCode: Int)
//            }
//        }
    }

    sealed class SignOut {
        class Start
        class Success
        data class Error(val someData: String)
    }




}