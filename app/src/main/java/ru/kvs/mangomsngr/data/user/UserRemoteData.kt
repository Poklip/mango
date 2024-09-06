package ru.kvs.mangomsngr.data.user

import ru.kvs.mangomsngr.models.user.CheckAuthBody
import ru.kvs.mangomsngr.models.user.ProfileToChangeBody
import ru.kvs.mangomsngr.models.user.RegistrationBody
import ru.kvs.mangomsngr.models.user.SendAuthBody
import javax.inject.Inject

class UserRemoteData @Inject constructor(private val service: UserService) {

    suspend fun sendAuth(phoneNumberForCode: SendAuthBody) =
        service.sendAuthCode(body = phoneNumberForCode)

    suspend fun checkAuth(phoneNumberWithCode: CheckAuthBody) =
        service.checkAuthCode(body = phoneNumberWithCode)

    suspend fun registerNewUser(registerInfo: RegistrationBody) =
        service.registerNewUser(body = registerInfo)

    suspend fun getUserProfile() =
        service.getUserData()

    suspend fun changeUserProfile(dataToRefresh: ProfileToChangeBody) =
        service.changeUserData(body = dataToRefresh)
}