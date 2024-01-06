package com.example.unihome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.unihome.models.Payment
import com.example.unihome.services.PaymentService
import com.example.unihome.utils.GlobalFunctions
import com.example.unihome.utils.GlobalVariables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PaymentActivity : AppCompatActivity() {
    private val paymentExitIV: ImageView by lazy { findViewById(R.id.payment_exit_arrow_iv) }
    private val paymentTypeTV: TextView by lazy { findViewById(R.id.payment_type_tv) }
    private val paymentDescriptionTextTV: TextView by lazy { findViewById(R.id.payment_description_text_tv) }
    private val paymentIssueDateTV: TextView by lazy { findViewById(R.id.payment_issue_date_tv) }
    private val paymentValueTV: TextView by lazy { findViewById(R.id.payment_value_tv) }
    private val paymentStateTV: TextView by lazy { findViewById(R.id.payment_state_tv) }
    private val payPaymentBT: Button by lazy { findViewById(R.id.payment_pay_bt) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val paymentID: String = intent.getStringExtra("ID").toString()

        getPaymentData(paymentID.toInt())

        paymentExitIV.setOnClickListener {
            finish()
        }
    }

    private fun getPaymentData(paymentID: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.UNIHOME_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val sp = GlobalFunctions.getSharedPreferencesContext(this@PaymentActivity)
        val authToken = sp.getString("authToken", null)

        val service = retrofit.create(PaymentService::class.java)
        val call = service.getPaymentById(authToken, paymentID)

        call.enqueue(object : Callback<Payment> {
            override fun onResponse(call: Call<Payment>, response: Response<Payment>) {
                if (response.code() == 200){
                    val payment = response.body()
                    paymentTypeTV.text = payment?.paymentType?.name
                    paymentDescriptionTextTV.text = payment?.description
                    paymentIssueDateTV.text = payment?.issueDate.toString().substring(0,10)
                    paymentValueTV.text = String.format("%.2f", payment?.value) + " €"
                    paymentStateTV.text = payment?.status
                }
            }
            override fun onFailure(call: Call<Payment>, t: Throwable) {
                print("error")
            }
        })
    }
}