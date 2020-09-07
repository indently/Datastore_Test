package com.federicocotogno.datastoretest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    lateinit var userManager: UserManager
    var age = 0
    var name = ""
    var gender = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Get reference to our userManager class
        userManager = UserManager(this)

        buttonSave()

        observeData()

    }

    private fun observeData() {

        //Updates age
        userManager.userAgeFlow.asLiveData().observe(this, {
            Log.d(TAG, "Age: $it")
            age = it

            tv_age.text = it.toString()
        })

        //Updates name
        userManager.userNameFlow.asLiveData().observe(this, {
            Log.d(TAG, "Name: $it")
            name = it

            tv_name.text = it.toString()
        })

        //Updates gender
        userManager.userGenderFlow.asLiveData().observe(this, {
            gender = if (it) "Male" else "Female"
            Log.d(TAG, "Gender: $gender")

            tv_gender.text = gender
        })
    }

    private fun buttonSave() {

        //Gets the user input and saves it
        btn_save.setOnClickListener {
            name = et_name.text.toString()
            age = et_age.text.toString().toInt()
            val isMale = switch_gender.isChecked

            //Stores the values
            GlobalScope.launch {
                userManager.storeUser(age, name, isMale)
            }
        }


    }

}