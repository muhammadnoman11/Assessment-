package com.example.assessmentfetchapi.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.assessmentfetchapi.db.model.UserEntity

@Dao
interface UserDao {

    /**Method to update a complete row if some fields are not changed,
     * they will be saved as null**/
//    @Insert(onConflict = OnConflictStrategy.REPLACE)

    @Insert
    suspend fun insertUsersIntoDb(users: List<UserEntity>)

    @Update
    suspend fun updateUserInDb(user: UserEntity)

    @Query("SELECT * FROM users")
    fun getAllUsersFromDb(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    suspend fun getUserFromDbById(userId: Int): UserEntity?
}