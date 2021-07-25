package lv.zesloka.data.user

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import lv.zesloka.domain.contract.user.UserRepo
import lv.zesloka.domain.model.User

class UserRepoImpl constructor(private val awsDataSource: UserDataSource) : UserRepo {
    override suspend fun getUser(): User {
        return withContext(Dispatchers.IO) {
            awsDataSource.getUser()
        }
    }
}