package com.example.unihome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.unihome.models.User
import com.example.unihome.services.UserService
import com.example.unihome.utils.GlobalFunctions
import com.example.unihome.utils.GlobalVariables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileActivity : AppCompatActivity() {
    private val profileExitIV: ImageView by lazy { findViewById(R.id.profile_exit_arrow_iv) }
    private val userNameTV: TextView by lazy { findViewById(R.id.profile_name_tv) }
    private val phoneNumberTV: TextView by lazy { findViewById(R.id.profile_phone_tv) }
    private val emailTV: TextView by lazy { findViewById(R.id.profile_email_tv) }
    private val cogWheel: ImageView by lazy {findViewById(R.id.profile_cog_wheel_iv)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val userID: String = intent.getStringExtra("userID").toString()

        getUserData(userID.toInt())

        profileExitIV.setOnClickListener {
            finish()
        }

        cogWheel.setOnClickListener {
            val intent = Intent(this@ProfileActivity, EditProfile::class.java)
            intent.putExtra("userID", userID)
            startActivity(intent)
        }
    }

    private fun getUserData(userID: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.UNIHOME_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val sp = GlobalFunctions.getSharedPreferencesContext(this@ProfileActivity)
        val authToken = sp.getString("authToken", null)

        val service = retrofit.create(UserService::class.java)
        val call = service.getUserById(authToken, userID)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.code() == 200) {
                    val user = response.body()
                    userNameTV.text = user?.name
                    phoneNumberTV.text = user?.phoneNumber.toString()
                    emailTV.text = user?.email
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                print("error")
            }
        })
    }
}