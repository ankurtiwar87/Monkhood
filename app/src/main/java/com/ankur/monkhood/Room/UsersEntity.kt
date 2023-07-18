package com.ankur.monkhood.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User_Product")
data class UsersEntity(

    @PrimaryKey
    val userId:String,
    @ColumnInfo(name = "UserName")
    val Name :String,
    @ColumnInfo(name = "Phone")
    val Phone:String,
    @ColumnInfo(name="email")
    val email:String,
    @ColumnInfo(name="ImgUrl")
    val img:String,
    @ColumnInfo(name = "date")
    val date :String

)
