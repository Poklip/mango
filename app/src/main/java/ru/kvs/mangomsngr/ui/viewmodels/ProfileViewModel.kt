package ru.kvs.mangomsngr.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kvs.mangomsngr.data.local.user.ProfileRepo
import ru.kvs.mangomsngr.data.remote.user.UserRepo
import ru.kvs.mangomsngr.models.user.AvatarExtended
import ru.kvs.mangomsngr.models.user.ProfileData
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val remoteRepo: UserRepo, private val localRepo: ProfileRepo) : ViewModel() {

    private val dbScope = CoroutineScope(Dispatchers.IO)

    fun getUserDataRemote(): LiveData<ProfileData?> {
        return liveData {
            val userDataResponse = remoteRepo.getUserData()
            userDataResponse.body()?.let { emit(it.profileData) }
        }
    }

    fun changeUserDataOnService(): LiveData<AvatarExtended?> {
        return liveData {
            val dataToRefresh = localRepo
                .getProfile()
                ?.toSendToService()
                ?: return@liveData
            val userDataRefreshedResponse = remoteRepo.changeUserData(dataToRefresh = dataToRefresh)
            userDataRefreshedResponse.body()?.let { emit(it.avatars) }
        }
    }

    fun getUserDataLocal(): LiveData<ProfileData?> {
        return liveData {
            emit(localRepo.getProfile())
        }
    }

    fun saveUserData(profileData: ProfileData?) {
        if (profileData == null) return
        dbScope.launch {
            localRepo.saveProfile(profileData)
        }
    }
}