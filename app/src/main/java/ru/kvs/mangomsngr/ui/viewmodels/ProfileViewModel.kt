package ru.kvs.mangomsngr.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kvs.mangomsngr.data.local.user.UserLocalRepo
import ru.kvs.mangomsngr.data.remote.user.UserRemoteRepo
import ru.kvs.mangomsngr.models.user.AvatarExtended
import ru.kvs.mangomsngr.models.user.ProfileData
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val remoteRepo: UserRemoteRepo, private val localRepo: UserLocalRepo) : ViewModel() {

    private val dbScope = CoroutineScope(Dispatchers.IO)

    private fun getUserDataRemote(): LiveData<ProfileData?> {
        return liveData {
            val accessToken = localRepo.getAccessToken()
            var userDataResponse = remoteRepo.getUserData(
                accessToken ?: throw NullPointerException("no access token has been saved")
            )

            if (userDataResponse.code() == 401) {
                val refreshToken = localRepo.getRefreshToken() ?: throw NullPointerException("no refresh token has been saved")
                val refreshedAccessToken = remoteRepo.refreshToken(refreshToken).body()?.accessToken
                localRepo.updateAccessToken(
                    refreshedAccessToken ?: throw NullPointerException("error getting access token")
                )
                userDataResponse = remoteRepo.getUserData(refreshedAccessToken)
            }

            userDataResponse.body()?.let { emit(it.profileData) }
        }
    }

    fun changeUserDataOnService(): LiveData<AvatarExtended?> {
        return liveData {
            val dataToRefresh = localRepo
                .getProfile()
                ?.toSendToService()
                ?: return@liveData
            val accessToken = localRepo.getAccessToken()
            var userDataRefreshedResponse = remoteRepo.changeUserData(
                dataToRefresh = dataToRefresh,
                accessToken = accessToken
                    ?: throw NullPointerException("no access token has been saved")
            )
            if (userDataRefreshedResponse.code() == 401) {
                val refreshToken = localRepo.getRefreshToken() ?: throw NullPointerException("no refresh token has been saved")
                val refreshedAccessToken = remoteRepo.refreshToken(refreshToken).body()?.accessToken
                localRepo.updateAccessToken(
                    refreshedAccessToken ?: throw NullPointerException("error getting access token")
                )
                userDataRefreshedResponse = remoteRepo.changeUserData(
                    dataToRefresh = dataToRefresh,
                    accessToken = refreshedAccessToken
                )
            }

            userDataRefreshedResponse.body()?.let { emit(it.avatars) }
        }
    }

    fun getUserDataLocal(): LiveData<ProfileData?> {
        return liveData {
            var profileData = localRepo.getProfile()
            if (profileData == null) {
                profileData = getUserDataRemote().value
            }
            emit(profileData)
        }
    }

    fun updateUserData(profileData: ProfileData?) {
        if (profileData == null) return
        dbScope.launch {
            localRepo.updateProfile(profileData)
        }
    }
}