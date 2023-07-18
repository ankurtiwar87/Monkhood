package com.ankur.monkhood

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.ankur.monkhood.Room.AppDatabase
import com.ankur.monkhood.Room.UsersEntity
import com.ankur.monkhood.databinding.ActivityMainBinding
import com.ankur.monkhood.firebaseDatabase.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*


class MainActivity : AppCompatActivity() {


//    private lateinit var mainViewModel:UserViewModel
    private lateinit var binding: ActivityMainBinding

    private lateinit var dialog: Dialog
    private var coverImageUrl:String ?= ""


    private var imgUrl: Uri? = null
    private var launchGalleyActivity =registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {

        if (it.resultCode== Activity.RESULT_OK)
        {
            imgUrl=it.data!!.data
            binding.imageView.setImageURI(imgUrl)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialog= Dialog(this)
        dialog.setContentView(R.layout.progress_dialog)
        dialog.setCancelable(false)

        binding.imageView.setOnClickListener {
            val intent = Intent("android.intent.action.GET_CONTENT")
            intent.type="image/*"
            launchGalleyActivity.launch(intent)
        }

        binding.datePickerActions.setOnClickListener {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(this, { View, year, monthOfYear, dayOfMonth ->

                    binding.datePickerActions.text = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                }, year, month, day
            )
            datePickerDialog.show()
        }

        binding.button.setOnClickListener {

            validateData()

            }

        binding.button2.setOnClickListener {
            Intent(this,DataShowing::class.java).also {
                startActivity(it)
            }
        }






    }

    private fun validateData() {

        if(binding.Name.text.toString().isEmpty())
        {
            binding.Name.requestFocus()
            binding.Name.error="Empty"
        }
        else if (binding.Phone.text.toString().length>10 || binding.Phone.text.toString().length<10)
        {
            binding.Phone.requestFocus()
            binding.Phone.error="Give 10 Digit Number"
        }
        else if (binding.email.text.toString().isEmpty())
        {
            binding.email.requestFocus()
            binding.email.error="Empty"
        }
        else if (binding.datePickerActions.text.toString().isEmpty())
        {
            binding.datePickerActions.requestFocus()
            binding.datePickerActions.error="Empty"

        }
        else if (imgUrl==null)
        {

            Toast.makeText(this, "Choose an Image", Toast.LENGTH_SHORT).show()
        }
        else
        {
            uploadImage()
        }



    }

    private fun uploadImage() {
        dialog.show()

        val fileName = UUID.randomUUID().toString()+".jpg"

        val refStorage = FirebaseStorage.getInstance().reference.child("user/$fileName")
        refStorage.putFile(imgUrl!!)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener { Image  ->
//                    Data to be upload on firebase
                    uploadData(Image.toString())
                }
            }.addOnFailureListener{
                dialog.dismiss()
                Toast.makeText(this, "Something went wrong with Storage", Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadData(ImageUrl: String) {

        val db = Firebase.firestore.collection("User")
        val key = db.document().id
        val userDao = AppDatabase.getDatabase(this).userDao()

        val data = User(key,ImageUrl,
        binding.Name.text.toString(),binding.Phone.text.toString(),binding.email.text.toString(),
        binding.datePickerActions.text.toString())

        val data2 =UsersEntity(key,binding.Name.text.toString(),binding.Phone.text.toString(),binding.email.text.toString(),
        ImageUrl,binding.datePickerActions.text.toString())


        lifecycleScope.launch(Dispatchers.IO) {
            db.document(key).set(data).await()
            userDao.insert(data2)

            withContext(Dispatchers.Main){
                dialog.dismiss()
                Toast.makeText(applicationContext, "Data Stored", Toast.LENGTH_SHORT).show()

                Intent(applicationContext,DataShowing::class.java).also {
                    startActivity(it)
                }

            }


        }

    }
}


