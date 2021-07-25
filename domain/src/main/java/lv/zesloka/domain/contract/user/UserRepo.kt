package lv.zesloka.domain.contract.user

import lv.zesloka.domain.model.User

public interface UserRepo {
    suspend fun getUser(): User
}