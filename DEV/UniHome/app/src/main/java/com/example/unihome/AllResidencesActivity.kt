package com.example.unihome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import com.example.unihome.adapters.AllResidencesArrayAdapter
import com.example.unihome.models.Residence
import com.example.unihome.services.ResidenceService
import com.example.unihome.utils.GlobalFunctions
import com.example.unihome.utils.GlobalVariables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AllResidencesActivity : AppCompatActivity() {
    private val allResidencesExitBT: ImageView by lazy { findViewById(R.id.allresidences_exit_arrow_iv) }
    private val createResidenceBT: Button by lazy { findViewById(R.id.allresidences_create_bt) }
    private val allResidencesLV: ListView by lazy { findViewById(R.id.allresidences_residences_lv) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_residences)

        if (GlobalFunctions.isNetworkAvailable(applicationContext))
            getResidences()

        createResidenceBT.setOnClickListener {
            val intent = Intent(this@AllResidencesActivity, CreateResidenceActivity::class.java)
            startActivityForResult(intent, 1)
        }

        allResidencesExitBT.setOnClickListener {
            finish()
        }
    }

    private fun getResidences() {
        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.UNIHOME_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val sp = GlobalFunctions.getSharedPreferencesContext(this@AllResidencesActivity)
        val authToken = sp.getString("authToken", null)

        val service = retrofit.create(ResidenceService::class.java)
        val call = service.getAllResidences(authToken)

        call.enqueue(object : Callback<List<Residence>> {
            override fun onResponse(call: Call<List<Residence>>, response: Response<List<Residence>>) {
                if (response.code() == 200){
                    val residences = response.body()
                    residences?.let {
                        val mutableResidences = it.toMutableList()
                        val adapter = AllResidencesArrayAdapter(this@AllResidencesActivity, R.layout.layout_all_residences_lv, mutableResidences)
                        allResidencesLV.adapter = adapter
                    }
                }
            }
            override fun onFailure(call: Call<List<Residence>>, t: Throwable) {
                print("error")
            }
        })
    }
}