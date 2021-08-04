package lv.zesloka.skani.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import lv.zesloka.data.user.AWSUserDataSource
import lv.zesloka.data.user.AWSUserManager
import lv.zesloka.data.user.UserDataSource
import lv.zesloka.data.user.UserApiImpl
import lv.zesloka.domain.contract.user.UserApi
import lv.zesloka.domain.usecase.user.*
import javax.inject.Singleton

@Module(includes = [ApplicationProviderModule::class])
class UserModule {

    @Provides
    @Singleton
    fun provideAWSUserManager(application: Application): AWSUserManager =
        AWSUserManager(application.applicationContext)

    @Provides
    fun provideAWSDataSource(awsUserManager: AWSUserManager): UserDataSource =
        AWSUserDataSource(awsUserManager)

    @Provides
    fun provideGetUserUseCase(userRepo: UserApi): GetUserStateUseCase =
        GetUserStateUseCase(userRepo)

    @Provides
    fun providesSignUpUserUseCase(userApi: UserApi): SignUpUserUseCase =
        SignUpUserUseCase(userApi)

    @Provides
    fun providesVerifyEmailUseCase(userApi: UserApi): VerifyEmailUseCase =
        VerifyEmailUseCase(userApi)

    @Provides
    fun providesSignInUserUseCase(userApi: UserApi): SignInUserUseCase =
        SignInUserUseCase(userApi)

    @Provides
    fun providesSignOutUserUseCase(userApi: UserApi): SignOutUserUseCase =
        SignOutUserUseCase(userApi)

    @Provides
    @Singleton
    fun providesUserApi(awsUserDataSource: UserDataSource): UserApi =
        UserApiImpl(awsUserDataSource)

}