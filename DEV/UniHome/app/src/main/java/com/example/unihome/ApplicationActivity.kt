package com.example.unihome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.example.unihome.models.Application
import com.example.unihome.services.ApplicationService
import com.example.unihome.utils.GlobalFunctions
import com.example.unihome.utils.GlobalVariables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationActivity : AppCompatActivity() {
    private val applicationExitIV: ImageView by lazy { findViewById(R.id.application_exit_arrow_iv) }
    private val applicationIdTV: TextView by lazy { findViewById(R.id.application_id_tv) }
    private val usernameTV: TextView by lazy { findViewById(R.id.application_name_tv) }
    private val emailTV: TextView by lazy { findViewById(R.id.application_email_tv) }
    private val nifTV: TextView by lazy { findViewById(R.id.application_nif_tv) }
    private val phoneNumberTV: TextView by lazy { findViewById(R.id.application_phone_number_tv) }
    private val sosNumberTV: TextView by lazy { findViewById(R.id.application_sos_number_tv) }
    private val nationalityTV: TextView by lazy { findViewById(R.id.application_nationality_tv) }
    private val genderTV: TextView by lazy { findViewById(R.id.application_gender_tv) }
    private val residenceTV: TextView by lazy { findViewById(R.id.application_residence_tv) }
    private val roomTypeTV: TextView by lazy { findViewById(R.id.application_room_type_tv) }
    private val courseNameTV: TextView by lazy { findViewById(R.id.application_course_name_tv) }
    private val courseYearTV: TextView by lazy { findViewById(R.id.application_course_year_tv) }
    private val courseStartTV: TextView by lazy { findViewById(R.id.application_course_start_tv) }
    private val lastYearStatusCB: CheckBox by lazy { findViewById(R.id.application_last_year_status_cb) }
    private val scholarshipCB: CheckBox by lazy { findViewById(R.id.application_scholarship_cb) }
    private val observationsTV: TextView by lazy { findViewById(R.id.application_observations_tv) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_application)

        val applicationID: String = intent.getStringExtra("ID").toString()
        val applicationTitle = "Apply ID: $applicationID"
        applicationIdTV.text = applicationTitle

        getApplicationByIdData(applicationID.toInt())

        applicationExitIV.setOnClickListener{
            finish()
        }
    }

    private fun getApplicationByIdData(appId: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.UNIHOME_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val sp = GlobalFunctions.getSharedPreferencesContext(this@ApplicationActivity)
        val authToken = sp.getString("authToken", null)

        val service = retrofit.create(ApplicationService::class.java)
        val call = service.getApplicationById(authToken, appId)

        call.enqueue(object : Callback<Application> {
            override fun onResponse(call: Call<Application>, response: Response<Application>) {
                if (response.code() == 200){
                    val application = response.body()
                    val fullName = application?.user?.name + " " + application?.user?.surname

                    usernameTV.text = fullName
                    emailTV.text = application?.user?.email
                    nifTV.text = application?.user?.nif
                    phoneNumberTV.text = application?.user?.phoneNumber.toString()
                    sosNumberTV.text = if (application?.sosNumber != null) application.sosNumber.toString() else "-----"
                    nationalityTV.text = application?.user?.nationality
                    genderTV.text = application?.user?.gender
                    residenceTV.text = application?.residence?.name
                    roomTypeTV.text = application?.roomType?.name
                    courseNameTV.text = application?.courseName
                    courseYearTV.text = application?.courseYearAttended
                    courseStartTV.text = application?.courseYearStarted
                    lastYearStatusCB.isChecked = application?.lastYearStatus == true
                    scholarshipCB.isChecked = application?.socialBenefits == true
                    observationsTV.text = if (application?.observations != null) application.observations.toString() else "-----"
                }
            }
            override fun onFailure(call: Call<Application>, t: Throwable) {
                print("error")
            }
        })
    }
}