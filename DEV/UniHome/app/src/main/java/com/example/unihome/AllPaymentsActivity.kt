package com.example.unihome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import com.example.unihome.adapters.AllPaymentsArrayAdapter
import com.example.unihome.models.Payment
import com.example.unihome.services.PaymentService
import com.example.unihome.utils.GlobalFunctions
import com.example.unihome.utils.GlobalVariables.UNIHOME_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AllPaymentsActivity : AppCompatActivity() {
    private val allPaymentsLV: ListView by lazy { findViewById(R.id.allpayments_payments_lv) }
    private val paymentExitIV: ImageView by lazy { findViewById(R.id.allpayments_exit_arrow_iv) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_payments)

        if(GlobalFunctions.isNetworkAvailable(applicationContext)){
            val userID: String? = intent.getStringExtra("userID")
            if (userID != null) {
                getPaymentsData(userID.toInt())
            }
            allPaymentsLV.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
                val paymentId = (adapterView.adapter as AllPaymentsArrayAdapter).getItem(position)?.ID
                val intent = Intent(this@AllPaymentsActivity, PaymentActivity::class.java)

                intent.putExtra("ID", paymentId.toString())
                startActivity(intent)
            }
        }
        else{
            Toast.makeText(this, "No Internet!", Toast.LENGTH_LONG).show()
        }

        paymentExitIV.setOnClickListener{
            finish()
        }
    }

    private fun getPaymentsData(userID: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(UNIHOME_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val sp = GlobalFunctions.getSharedPreferencesContext(this@AllPaymentsActivity)
        val authToken = sp.getString("authToken", null)

        val service = retrofit.create(PaymentService::class.java)
        val call = service.getPaymentsByUserId(authToken, userID)

        call.enqueue(object : Callback<List<Payment>> {
            override fun onResponse(call: Call<List<Payment>>, response: Response<List<Payment>>) {
                if (response.code() == 200){
                    val payments = response.body()
                    payments?.let {
                        val adapter = AllPaymentsArrayAdapter(this@AllPaymentsActivity, R.layout.layout_all_payments_lv, it)
                        allPaymentsLV.adapter = adapter
                    }
                }
            }
            override fun onFailure(call: Call<List<Payment>>, t: Throwable) {
                print("error")
            }
        })
    }
}