package com.example.unihome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class EditProfile : AppCompatActivity() {

    private val editProfileExitIV: ImageView by lazy { findViewById(R.id.edit_profile_exit_arrow_iv)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val userId: String = intent.getStringExtra("userID").toString()
        
        editProfileExitIV.setOnClickListener {
            finish()
        }
    }
}