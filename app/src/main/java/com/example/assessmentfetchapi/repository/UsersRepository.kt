package com.example.assessmentfetchapi.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.assessmentfetchapi.api.ApiService
import com.example.assessmentfetchapi.api.NetworkResult
import com.example.assessmentfetchapi.db.UserDao
import com.example.assessmentfetchapi.db.model.UserEntity
import com.example.assessmentfetchapi.model.UserModelItem
import retrofit2.Response
import javax.inject.Inject
import kotlin.math.log

class UsersRepository @Inject constructor(
    private val apiService: ApiService, private val userDao: UserDao) {


    private val _userResponseApi = MutableLiveData<NetworkResult<List<UserModelItem>>>()
    val userResponseApi: LiveData<NetworkResult<List<UserModelItem>>>
        get() = _userResponseApi



    private var fetchedUsers: List<UserModelItem>? = null

    suspend fun getUsersFromApi() {
        if (fetchedUsers != null) {
            _userResponseApi.postValue(NetworkResult.Success(fetchedUsers))
            return
        }
        try {
            _userResponseApi.postValue(NetworkResult.Loading())
            val response = apiService.getUsersFromApi()
            handleResponse(response)
        } catch (e: Exception) {
            Log.d("TAG", "getUsersFromApiRepo: ${e.message}")
            _userResponseApi.postValue(NetworkResult.Error(e.message ?: "Something went wrong"))
        }
    }


    private suspend fun handleResponse(response: Response<List<UserModelItem>>) {
        if (response.isSuccessful && response.body() != null) {
//            val fetchedUsers = response.body()!!
            fetchedUsers = response.body()!!

            for (fetchedUser in fetchedUsers!!) {
                val existingUser = userDao.getUserFromDbById(fetchedUser.id)

                if (existingUser != null) {
                    /** Compare the fetched user with the existing user */
                    if (existingUser != fetchedUser.toUserEntity()) {
                        userDao.updateUserInDb(fetchedUser.toUserEntity())
                    }
                } else {
                    /** If the user doesn't exist, insert it */
                    userDao.insertUsersIntoDb(listOf(fetchedUser.toUserEntity()))
                }
            }

            _userResponseApi.postValue(NetworkResult.Success(fetchedUsers))
        } else {
            val errorMessage = response.errorBody()?.string() ?: "Something went wrong"
            _userResponseApi.postValue(NetworkResult.Error(errorMessage))
        }
    }


    fun getUsersFromDb(): LiveData<List<UserEntity>> {
        return userDao.getAllUsersFromDb()
    }



}


fun UserModelItem.toUserEntity(): UserEntity { /** Extension function Maps the properties of UserModelItem to UserEntity, facilitating the insertion and update processes */
    return UserEntity(
        id = id,
        name = name,
        company = company,
        username = username,
        email = email,
        address = address,
        zip = zip,
        state = state,
        country = country,
        phone = phone,
        photo = photo
    )
}