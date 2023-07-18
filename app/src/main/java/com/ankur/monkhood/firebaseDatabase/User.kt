package com.ankur.monkhood.firebaseDatabase

data class User(
    val userId:String ?= null,
    val img :String ?= null,
    val name:String ="",
    val Phone:String ="",
    val email:String ="",
    val date:String =""

)
