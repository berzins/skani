package lv.zesloka.skani.di

import android.app.Application
import dagger.Module
import dagger.Provides
import lv.zesloka.data.user.AWSUserDataSource
import lv.zesloka.data.user.AWSUserManager
import lv.zesloka.data.user.UserDataSource
import lv.zesloka.data.user.UserRepoImpl
import lv.zesloka.domain.contract.user.UserRepo
import lv.zesloka.domain.usecase.user.GetUserStateUseCase
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
    fun provideGetUserUseCase(userRepo: UserRepo): GetUserStateUseCase =
        GetUserStateUseCase(userRepo)

    @Provides
    @Singleton
    fun provideUserRepo(awsUserDataSource: UserDataSource): UserRepo =
        UserRepoImpl(awsUserDataSource)

}