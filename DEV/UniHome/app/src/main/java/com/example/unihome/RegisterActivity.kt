package com.example.unihome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.unihome.models.User
import com.example.unihome.services.AuthService
import com.example.unihome.utils.GlobalFunctions
import com.example.unihome.utils.GlobalVariables
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    private val registerSignInBT: Button by lazy { findViewById(R.id.register_signup_bt) }
    private val registerAlreadyHaveAccountTV: TextView by lazy { findViewById(R.id.register_already_have_account_tv) }
    private val firstNameET: EditText by lazy { findViewById(R.id.register_name_et) }
    private val lastNameET: EditText by lazy { findViewById(R.id.register_surname_et) }
    private val emailET: EditText by lazy { findViewById(R.id.register_email_et) }
    private val passwordET: EditText by lazy { findViewById(R.id.register_password_et) }
    private val confirmPasswordET: EditText by lazy { findViewById(R.id.register_confirm_password_et) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerSignInBT.setOnClickListener {
            if(GlobalFunctions.isNetworkAvailable(applicationContext)) {
                if (firstNameET.text.isNotBlank() &&
                    lastNameET.text.isNotBlank() &&
                    emailET.text.isNotBlank() &&
                    passwordET.text.isNotBlank() &&
                    confirmPasswordET.text.isNotBlank()
                )
                    authRegister()
                else
                    Toast.makeText(this, "Please fill all required fields!", Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(this, "No Internet!", Toast.LENGTH_LONG).show()
        }

        registerAlreadyHaveAccountTV.setOnClickListener {
            finish()
        }
    }

    private fun authRegister() {
        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.UNIHOME_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(AuthService::class.java)

        val userBody =  User(
            null,
            firstNameET.text.toString(),
            lastNameET.text.toString(),
            emailET.text.toString(),
            passwordET.text.toString(),
            confirmPasswordET.text.toString(),
            null,
            null,
            null,
            null,
            null,
            null,
            "temp",
            null
        )
        val call = service.registerUser(userBody)

        call.enqueue(object : Callback<Map<String, String>> {
            override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                if (response.code() == 200){
                    val authorizationToken = (response.body())?.get("Authorization")

                    val sp = GlobalFunctions.getSharedPreferencesContext(this@RegisterActivity)
                    sp.edit().putString("authToken", authorizationToken).apply()

                    val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
                    intent.putExtra("authToken", authorizationToken)
                    startActivity(intent)
                    finish()
                }
                else if (response.code() == 409){
                    val errorBody = (response.errorBody())?.string()
                    val jsonObject = errorBody?.let { JSONObject(it) }
                    val error = jsonObject?.getString("error")
                    Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                print("error")
            }
        })
    }
}