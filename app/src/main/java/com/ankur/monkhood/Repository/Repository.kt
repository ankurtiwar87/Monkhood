package com.ankur.monkhood.Repository

import androidx.lifecycle.LiveData
import com.ankur.monkhood.Room.UserDao
import com.ankur.monkhood.Room.UsersEntity
import com.ankur.monkhood.firebaseDatabase.User

class Repository(private val dao: UserDao ) {

    fun insert(user: UsersEntity)
    {
        dao.insert(user)
    }

    fun delete(user: UsersEntity){
        dao.delete(user)
    }
    fun getAllData(): LiveData<List<UsersEntity>>{

        return dao.getAllData()
    }




}