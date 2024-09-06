package ru.kvs.mangomsngr.data.user

import ru.kvs.mangomsngr.models.user.CheckAuthBody
import ru.kvs.mangomsngr.models.user.ProfileToChangeBody
import ru.kvs.mangomsngr.models.user.RegistrationBody
import ru.kvs.mangomsngr.models.user.SendAuthBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepo @Inject constructor(private val userRemoteData: UserRemoteData) {

    suspend fun sendAuth(phoneNumberForCode: SendAuthBody) =
        userRemoteData.sendAuth(phoneNumberForCode = phoneNumberForCode)

    suspend fun checkAuth(phoneNumberWithCode: CheckAuthBody) =
        userRemoteData.checkAuth(phoneNumberWithCode = phoneNumberWithCode)

    suspend fun registerNewUser(registerInfo: RegistrationBody) =
        userRemoteData.registerNewUser(registerInfo = registerInfo)

    suspend fun getUserData() =
        userRemoteData.getUserProfile()

    suspend fun changeUserData(dataToRefresh: ProfileToChangeBody) =
        userRemoteData.changeUserProfile(dataToRefresh = dataToRefresh)

    suspend fun refreshToken() = userRemoteData.refreshAccessToken()

    suspend fun checkJwt() = userRemoteData.checkJwt()

}