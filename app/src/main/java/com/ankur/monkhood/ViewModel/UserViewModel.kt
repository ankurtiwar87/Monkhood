package com.ankur.monkhood.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ankur.monkhood.Repository.Repository
import com.ankur.monkhood.Room.UsersEntity
import com.ankur.monkhood.firebaseDatabase.User

class UserViewModel(private val repository: Repository):ViewModel() {


    fun insert(user: UsersEntity)
    {
        repository.insert(user)
    }

    fun delete(user: UsersEntity){
        repository.delete(user)
    }
    fun getAllData(): LiveData<List<UsersEntity>> {

        return repository.getAllData()
    }







}