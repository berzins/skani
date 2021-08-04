package lv.zesloka.skani.di.module

import dagger.Module
import dagger.Provides
import lv.zesloka.domain.usecase.validation.ValidateEmailUseCase
import lv.zesloka.domain.usecase.validation.ValidatePasswordUseCase
import lv.zesloka.domain.usecase.validation.ValidateUsernameUseCase

@Module
class ValidationModule {

    @Provides
    fun providesValidateUsernameUseCase() = ValidateUsernameUseCase()

    @Provides
    fun providesValidateEmailUseCase() = ValidateEmailUseCase()

    @Provides
    fun providesValidatePasswordUseCase() = ValidatePasswordUseCase()



}