package lv.zesloka.data.user

import lv.zesloka.domain.model.User

interface UserDataSource {
    suspend fun getUser(): User
    suspend fun saveUser(email: String, password: String, nickname: String)
}