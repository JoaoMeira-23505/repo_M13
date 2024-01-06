package com.example.unihome

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import com.example.unihome.adapters.AllCleaningsArrayAdapter
import com.example.unihome.models.Cleaning
import com.example.unihome.services.CleaningService
import com.example.unihome.utils.GlobalFunctions
import com.example.unihome.utils.GlobalVariables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AllCleaningsByUserActivity : AppCompatActivity() {
    private val allCleaningsExitBT: ImageView by lazy { findViewById(R.id.allcleanings_exit_arrow_iv) }
    private val scheduleCleaningBT: Button by lazy { findViewById(R.id.allcleanings_create_bt) }
    private val allCleaningsLV: ListView by lazy { findViewById(R.id.allcleanings_cleanings_lv) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_cleanings_by_user)

        val userID: String? = intent.getStringExtra("userID")

        if (userID != null)
            getCleanings(userID.toInt())

        scheduleCleaningBT.setOnClickListener {
            if (userID != null) {
                val intent = Intent(this@AllCleaningsByUserActivity, ScheduleCleaningActivity::class.java)
                intent.putExtra("userID", userID)
                startActivityForResult(intent, 1)
            }
        }

        allCleaningsExitBT.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val date = data?.getStringExtra("date")
                val status = data?.getStringExtra("status")

                val newCleaning = Cleaning(
                    null,
                    date,
                    status,
                    null,
                    null,
                    null,
                    null
                )

                val adapter = allCleaningsLV.adapter as? AllCleaningsArrayAdapter
                adapter?.addCleaning(newCleaning)
                allCleaningsLV.postDelayed({
                    adapter?.notifyDataSetChanged()
                    allCleaningsLV.smoothScrollToPosition(adapter?.count ?: 0)
                }, 50)

                Toast.makeText(this, "Cleaning successfully scheduled", Toast.LENGTH_SHORT).show()
            }
            else if (resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this, "Something didn't work", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCleanings(userID: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.UNIHOME_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val sp = GlobalFunctions.getSharedPreferencesContext(this@AllCleaningsByUserActivity)
        val authToken = sp.getString("authToken", null)

        val service = retrofit.create(CleaningService::class.java)
        val call = service.getAllCleaningsByUserId(authToken, userID)

        call.enqueue(object : Callback<List<Cleaning>> {
            override fun onResponse(call: Call<List<Cleaning>>, response: Response<List<Cleaning>>) {
                if (response.code() == 200){
                    val cleanings = response.body()
                    cleanings?.let {
                        val mutableCleanings = it.toMutableList()
                        val adapter = AllCleaningsArrayAdapter(this@AllCleaningsByUserActivity, R.layout.layout_all_cleanings_lv, mutableCleanings)
                        allCleaningsLV.adapter = adapter
                    }
                }
            }
            override fun onFailure(call: Call<List<Cleaning>>, t: Throwable) {
                print("error")
            }
        })
    }
}