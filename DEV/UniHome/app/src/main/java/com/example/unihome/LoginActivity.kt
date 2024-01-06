package com.example.unihome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.unihome.utils.GlobalVariables
import com.example.unihome.utils.GlobalFunctions
import com.example.unihome.models.User
import com.example.unihome.services.AuthService
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private val emailET: EditText by lazy { findViewById(R.id.login_email_et) }
    private val passwordET: EditText by lazy { findViewById(R.id.login_password_et) }
    private val logInBT: Button by lazy { findViewById(R.id.login_log_in_bt) }
    private val signUpTV: TextView by lazy { findViewById(R.id.login_dont_have_acc_tv) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        isLoggedIn()

        logInBT.setOnClickListener {
            if(GlobalFunctions.isNetworkAvailable(applicationContext)){
                if (emailET.text.isNotEmpty() && passwordET.text.isNotEmpty())
                    authUser()
                else
                    Toast.makeText(this, "Missing email or password",Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(this, "No Internet!", Toast.LENGTH_LONG).show()
        }

        signUpTV.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isLoggedIn() {
        val sp = GlobalFunctions.getSharedPreferencesContext(this@LoginActivity)
        val auth = sp.getString("authToken", null)

        if (auth != null) {
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            intent.putExtra("authToken", auth)
            startActivity(intent)
            finish()
        }
    }

    private fun authUser() {
        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.UNIHOME_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(AuthService::class.java)

        val userBody =  User(
            null,
            null,
            null,
            emailET.text.toString(),
            passwordET.text.toString(),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
        val call = service.authUser(userBody)

        call.enqueue(object : Callback<Map<String, String>> {
            override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                if (response.code() == 200){
                    val authorizationToken = (response.body())?.get("Authorization")

                    val sp = GlobalFunctions.getSharedPreferencesContext(this@LoginActivity)
                    sp.edit().putString("authToken", authorizationToken).apply()

                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    intent.putExtra("authToken", authorizationToken)
                    startActivity(intent)
                    finish()
                }
                else if (response.code() == 403){
                    val errorBody = (response.errorBody())?.string()
                    if (errorBody != null) {
                        val jsonObject = JSONObject(errorBody)
                        val error = jsonObject.getString("error")
                        Toast.makeText(applicationContext, error,Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(applicationContext, "Something went wrong!",Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                print("error")
            }
        })
    }
}