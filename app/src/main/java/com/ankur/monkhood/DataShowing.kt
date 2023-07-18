package com.ankur.monkhood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ankur.monkhood.Repository.Repository
import com.ankur.monkhood.Room.AppDatabase
import com.ankur.monkhood.Room.UsersEntity
import com.ankur.monkhood.ViewModel.UserViewModel
import com.ankur.monkhood.ViewModel.UserViewModelFactory
import com.ankur.monkhood.databinding.ActivityDataShowingBinding

class DataShowing : AppCompatActivity() {

    private lateinit var binding: ActivityDataShowingBinding
    private lateinit var mainViewModel:UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDataShowingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = AppDatabase.getDatabase(this).userDao()
        val repository= Repository(dao)

        mainViewModel= ViewModelProvider(this, UserViewModelFactory(repository))[UserViewModel::class.java]

        dao.getAllData().observe(this, Observer {
            binding.RecyclerView.adapter=adapter(applicationContext,it as ArrayList<UsersEntity>)
        })
    }
}