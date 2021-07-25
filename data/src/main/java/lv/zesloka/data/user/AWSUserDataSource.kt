package lv.zesloka.data.user

import lv.zesloka.domain.model.User

 public class AWSUserDataSource constructor(private val mUserManager: AWSUserManager): UserDataSource {

    override suspend fun getUser(): User {
        if (!mUserManager.isInitialized()) {
            mUserManager.init()
        }
        return mUserManager.getUser()
    }

    override suspend fun saveUser(email: String, password: String, nickname: String) {
        TODO("Not yet implemented")
    }

}