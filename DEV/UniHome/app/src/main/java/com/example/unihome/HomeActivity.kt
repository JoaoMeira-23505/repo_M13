package com.example.unihome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.unihome.utils.GlobalVariables
import com.example.unihome.models.User
import com.example.unihome.services.UserService
import com.example.unihome.utils.GlobalFunctions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {
    private val homeActivityCL: ConstraintLayout by lazy { findViewById(R.id.home_activity_ct) }
    private val testTV: TextView by lazy { findViewById(R.id.home_about_us_text_tv) }
    private val profileBT: ImageView by lazy { findViewById(R.id.home_profile_icon_iv) }
    private val openMenu: ImageView by lazy { findViewById(R.id.home_menu_icon_iv) }
    private val homeMenuLL: LinearLayout by lazy { findViewById(R.id.home_menu_ll) }
    private val menuChatBT: Button by lazy { findViewById(R.id.home_menu_chat_bt) }
    private val menuMyRoomBT: Button by lazy { findViewById(R.id.home_menu_my_room_bt) }
    private val menuChooseRoomTypeBT: Button by lazy { findViewById(R.id.home_menu_rooms_bt) }
    private val menuPaymentBT: Button by lazy { findViewById(R.id.home_menu_payment_bt) }
    private val menuCleaningBT: Button by lazy { findViewById(R.id.home_menu_cleaning_service_bt) }
    private val menuCleaningListBT: Button by lazy { findViewById(R.id.home_menu_cleaning_list_bt) }
    private val menuCheckApplicationsBT: Button by lazy { findViewById(R.id.home_menu_check_applications_bt) }
    private val menuListRentersBT: Button by lazy { findViewById(R.id.home_menu_list_renters_bt) }
    private val menuListResidenceBT: Button by lazy { findViewById(R.id.home_menu_list_residences_bt) }
    private val menuLogOutBT: Button by lazy { findViewById(R.id.home_menu_logout_bt) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val token: String? = intent.getStringExtra("authToken")
        if (token != null) {
            val userDecoded = GlobalFunctions.decodeJWTToken(token)
            val user = JSONObject(userDecoded).getString("user")
            val userID = JSONObject(user).getString("ID")
            val userName = JSONObject(user).getString("name")
            val userStatus = JSONObject(user).getString("status")
            val userRole = JSONObject(user).getString("role")
            testTV.text = "Welcome again $userName"

            if (userRole == "Normal") {
                menuCleaningListBT.visibility = View.GONE
                menuCheckApplicationsBT.visibility = View.GONE
                menuListRentersBT.visibility = View.GONE
                menuListResidenceBT.visibility = View.GONE

                /* TODO - THIS WILL ONLY WORK AS INTENDED WHEN THE REGISTER &
                    THE APPLICATION ARE FULLY IMPLEMENTED
                    AND ADD IT TO ABOVE IFs
                 */
//                if (userStatus == "Inactive" || userStatus == "Waiting") {
//                    menuMyRoomBT.visibility = View.GONE
//                    menuCleaningBT.visibility = View.GONE
//                }
            }
            else if (userRole == "Cleaning Staff"){
                menuChatBT.visibility = View.GONE
                menuMyRoomBT.visibility = View.GONE
                menuChooseRoomTypeBT.visibility = View.GONE
                menuPaymentBT.visibility = View.GONE
                menuCleaningBT.visibility = View.GONE
                menuCheckApplicationsBT.visibility = View.GONE
                menuListRentersBT.visibility = View.GONE
                menuListResidenceBT.visibility = View.GONE
            }

            getPushToken(userID.toInt())

            profileBT.setOnClickListener {
                val intent = Intent(this@HomeActivity, ProfileActivity::class.java)
                intent.putExtra("userID", userID)
                closeMenu()
                startActivity(intent)
            }

            menuChatBT.setOnClickListener {
                val intent = Intent(this@HomeActivity, AllChatsActivity::class.java)
                intent.putExtra("userID", userID)
                closeMenu()
                startActivity(intent)
            }
            menuMyRoomBT.setOnClickListener {
                val intent = Intent(this@HomeActivity, MyRoomActivity::class.java)
                intent.putExtra("userID", userID)
                closeMenu()
                startActivity(intent)
            }
            menuChooseRoomTypeBT.setOnClickListener {
                val intent = Intent(this@HomeActivity, ChooseRoomTypeActivity::class.java)
                intent.putExtra("userID", userID)
                closeMenu()
                startActivity(intent)
            }
            menuPaymentBT.setOnClickListener {
                val intent = Intent(this@HomeActivity, AllPaymentsActivity::class.java)
                intent.putExtra("userID", userID)
                closeMenu()
                startActivity(intent)
            }
            menuCleaningBT.setOnClickListener {
                val intent = Intent(this@HomeActivity, AllCleaningsByUserActivity::class.java)
                intent.putExtra("userID", userID)
                closeMenu()
                startActivity(intent)
            }
            menuCleaningListBT.setOnClickListener {
                val intent = Intent(this@HomeActivity, AllCleaningsActivity::class.java)
                intent.putExtra("userID", userID)
                closeMenu()
                startActivity(intent)
            }
            menuCheckApplicationsBT.setOnClickListener {
                val intent = Intent(this@HomeActivity, AllApplicationsActivity::class.java)
                closeMenu()
                startActivity(intent)
            }
            menuListRentersBT.setOnClickListener {
                val intent = Intent(this@HomeActivity, AllRentersActivity::class.java)
                intent.putExtra("userID", userID)
                closeMenu()
                startActivity(intent)
            }
            menuListResidenceBT.setOnClickListener {
                val intent = Intent(this@HomeActivity, AllResidencesActivity::class.java)
                intent.putExtra("userID", userID)
                closeMenu()
                startActivity(intent)
            }
        }
        else {
            Toast.makeText(this,"Something went terribly wrong!! Please Log In again.", Toast.LENGTH_LONG).show()
        }

        menuLogOutBT.setOnClickListener {
            val sp = GlobalFunctions.getSharedPreferencesContext(this@HomeActivity)
            sp.edit().putString("authToken", null).apply()

            val intent = Intent(this@HomeActivity, LoginActivity::class.java)
            closeMenu()
            finish()
            startActivity(intent)
        }

        openMenu.setOnClickListener {
            openMenu()
        }
        homeActivityCL.setOnClickListener {
            closeMenu()
        }
    }

    private fun openMenu(){
        val layoutParams = homeMenuLL.layoutParams as ConstraintLayout.LayoutParams

        if (layoutParams.endToStart == ConstraintLayout.LayoutParams.PARENT_ID) {
            layoutParams.endToStart = ConstraintLayout.LayoutParams.UNSET
            layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID

            homeMenuLL.layoutParams = layoutParams
        }
        else
            closeMenu()
    }

    private fun closeMenu(){
        val layoutParams = homeMenuLL.layoutParams as ConstraintLayout.LayoutParams

        if (layoutParams.startToStart == ConstraintLayout.LayoutParams.PARENT_ID) {
            layoutParams.startToStart = ConstraintLayout.LayoutParams.UNSET
            layoutParams.endToStart = ConstraintLayout.LayoutParams.PARENT_ID

            homeMenuLL.layoutParams = layoutParams
        }
    }

    private fun getPushToken(userID : Int) {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("Main", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                val token = task.result
                val msg = "InstanceID Token: $token"
                Log.d("Main", msg)

                patchUserToken(userID, token)
            })
    }

    private fun patchUserToken(userID : Int, token : String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.UNIHOME_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val userPatchBody =  User(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            token,
            null
        )
        val sp = GlobalFunctions.getSharedPreferencesContext(this@HomeActivity)
        val authToken = sp.getString("authToken", null)

        val service = retrofit.create(UserService::class.java)
        val call = service.updateUser(authToken, userID, userPatchBody)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.code() == 200){
                    Log.d("Patch token", "successful")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                print("error")
            }
        })
    }
}