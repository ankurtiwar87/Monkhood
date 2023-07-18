package com.ankur.monkhood.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   fun insert(user:UsersEntity)
    @Delete
    fun delete(user: UsersEntity)

    @Query("SELECT * FROM user_product")
    fun getAllData():LiveData<List<UsersEntity>>

}