package ru.kvs.mangomsngr.data.remote.user

import ru.kvs.mangomsngr.models.user.CheckAuthBody
import ru.kvs.mangomsngr.models.user.ProfileToChangeBody
import ru.kvs.mangomsngr.models.user.RegistrationBody
import ru.kvs.mangomsngr.models.user.SendAuthBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteRepo @Inject constructor(private val userRemoteData: UserRemoteData) {

    suspend fun sendAuth(phoneNumberForCode: SendAuthBody) =
        userRemoteData.sendAuth(phoneNumberForCode = phoneNumberForCode)

    suspend fun checkAuth(phoneNumberWithCode: CheckAuthBody) =
        userRemoteData.checkAuth(phoneNumberWithCode = phoneNumberWithCode)

    suspend fun registerNewUser(registerInfo: RegistrationBody) =
        userRemoteData.registerNewUser(registerInfo = registerInfo)

    suspend fun getUserData(accessToken: String) =
        userRemoteData.getUserProfile(accessToken)

    suspend fun changeUserData(dataToRefresh: ProfileToChangeBody, accessToken: String) =
        userRemoteData.changeUserProfile(accessToken = accessToken, dataToRefresh = dataToRefresh)

    suspend fun refreshToken(refreshToken: String)
        = userRemoteData.refreshAccessToken(refreshToken)

    suspend fun checkJwt()
        = userRemoteData.checkJwt()

}