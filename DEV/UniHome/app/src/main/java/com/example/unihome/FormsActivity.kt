package com.example.unihome

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.unihome.models.Application
import com.example.unihome.models.Residence
import com.example.unihome.models.User
import com.example.unihome.services.ApplicationService
import com.example.unihome.services.ResidenceService
import com.example.unihome.services.UserService
import com.example.unihome.utils.GlobalFunctions
import com.example.unihome.utils.GlobalVariables
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FormsActivity : AppCompatActivity() {
    private val formsExitIV: ImageView by lazy { findViewById(R.id.forms_exit_arrow_iv) }
    private val userFullNameET: EditText by lazy { findViewById(R.id.forms_full_name_et) }
    private val emailET: EditText by lazy { findViewById(R.id.forms_email_et) }
    private val nifET: EditText by lazy { findViewById(R.id.forms_nif_et) }
    private val phoneNumberET: EditText by lazy { findViewById(R.id.forms_telemovel_et) }
    private val sosNumberET: EditText by lazy { findViewById(R.id.forms_sos_number_et) }
    private val courseNameET: EditText by lazy { findViewById(R.id.forms_course_name_et) }
    private val roomTypeET: EditText by lazy { findViewById(R.id.forms_room_type_et) }
    private val observationsET: EditText by lazy { findViewById(R.id.forms_observations_et) }
    private val lastYearStatusCB: CheckBox by lazy { findViewById(R.id.forms_last_year_status_cb) }
    private val socialBenefitsCB: CheckBox by lazy { findViewById(R.id.forms_scholarship_cb) }
    private val firstTermsCB: CheckBox by lazy { findViewById(R.id.forms_first_terms_tv) }
    private val secondTermsCB: CheckBox by lazy { findViewById(R.id.forms_second_terms_tv) }
    private val submitApplicationBT: Button by lazy { findViewById(R.id.forms_submit_bt) }
    private val genderSP: Spinner by lazy {
        val spinner = findViewById<Spinner>(R.id.forms_gender_sp)
        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOf("-Gender-", "Male", "Female")
        )
        spinner
    }
    private val nationalitySP: Spinner by lazy {
        val spinner = findViewById<Spinner>(R.id.forms_nationality_sp)
        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOf("-Nationality-", "Portuguese", "English")
        )
        spinner
    }
    private val residencesSP: Spinner by lazy { findViewById(R.id.forms_residences_sp) }
    private val courseYearStartedSP: Spinner by lazy {
        val spinner = findViewById<Spinner>(R.id.forms_course_start_sp)
        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOf("-Year Started-","2024","2023","2022","2021","2020","2019","2018","2017")
        )
        spinner
    }
    private val currentCourseYearSP: Spinner by lazy {
        val spinner = findViewById<Spinner>(R.id.forms_course_year_sp)
        spinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOf("-Current Year-","1","2","3","4","5","6+")
        )
        spinner
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forms)

        val sp = GlobalFunctions.getSharedPreferencesContext(this@FormsActivity)
        val token: String? = sp.getString("authToken", null)
        val roomTypeID = intent.getStringExtra("roomTypeID")
        val roomTypeName = intent.getStringExtra("roomTypeName")

        if (token != null && roomTypeID != null && roomTypeName != null) {
            val userDecoded = GlobalFunctions.decodeJWTToken(token)
            val user = JSONObject(userDecoded).getString("user")
            val userID = JSONObject(user).getString("ID")
            roomTypeET.setText(roomTypeName.toString())

            getResidencesData()
            getUserData(userID.toInt())

            submitApplicationBT.setOnClickListener {
                if (userFullNameET.text.isNotBlank() &&
                    emailET.text.isNotBlank() &&
                    nifET.text.isNotBlank() &&
                    phoneNumberET.text.isNotBlank() &&
                    genderSP.selectedItemPosition != 0 &&
                    nationalitySP.selectedItemPosition != 0 &&
                    residencesSP.selectedItemPosition != 0 &&
                    courseNameET.text.isNotBlank() &&
                    currentCourseYearSP.selectedItemPosition != 0 &&
                    courseYearStartedSP.selectedItemPosition != 0 ) {
                    if (firstTermsCB.isChecked && secondTermsCB.isChecked) {
                        updateUserData(userID.toInt())
                        submitApplicationData(userID.toInt(), roomTypeID.toInt())
                    }
                    else
                        Toast.makeText(this, "Please accept all required terms!", Toast.LENGTH_SHORT).show()
                }
                else
                    Toast.makeText(this, "Please fill all required fields!", Toast.LENGTH_SHORT).show()
            }
        }

        formsExitIV.setOnClickListener{
            finish()
        }

        genderSP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                (parent!!.getChildAt(0) as TextView).setTextColor(
                    ContextCompat.getColor(applicationContext, R.color.lightBrown)
                )
                (parent.getChildAt(0) as TextView).textSize = 18f
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        nationalitySP.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                (parent!!.getChildAt(0) as TextView).setTextColor(
                    ContextCompat.getColor(applicationContext, R.color.lightBrown)
                )
                (parent.getChildAt(0) as TextView).textSize = 18f
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        residencesSP.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                (parent!!.getChildAt(0) as TextView).setTextColor(
                    ContextCompat.getColor(applicationContext, R.color.lightBrown)
                )
                (parent.getChildAt(0) as TextView).textSize = 18f
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        currentCourseYearSP.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                (parent!!.getChildAt(0) as TextView).setTextColor(
                    ContextCompat.getColor(applicationContext, R.color.lightBrown)
                )
                (parent.getChildAt(0) as TextView).textSize = 18f
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        courseYearStartedSP.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                (parent!!.getChildAt(0) as TextView).setTextColor(
                    ContextCompat.getColor(applicationContext, R.color.lightBrown)
                )
                (parent.getChildAt(0) as TextView).textSize = 18f
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun getUserData(userID: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.UNIHOME_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val sp = GlobalFunctions.getSharedPreferencesContext(this@FormsActivity)
        val authToken = sp.getString("authToken", null)

        val service = retrofit.create(UserService::class.java)
        val call = service.getUserById(authToken, userID)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.code() == 200) {
                    val user = response.body()
                    user?.let {
                        val fullName: String = user.name + " " + user.surname
                        userFullNameET.setText(fullName)
                        emailET.setText(user.email)
                        if (user.nif != null){
                            nifET.setText(user.nif)
                            nifET.isEnabled = false
                        }
                        if (user.phoneNumber != null){
                            phoneNumberET.setText(user.phoneNumber.toString())
                            phoneNumberET.isEnabled = false
                        }
                        when (user.gender) {
                            "Male" -> {
                                genderSP.setSelection(1)
                                genderSP.isEnabled = false
                            }
                            "Female" -> {
                                genderSP.setSelection(2)
                                genderSP.isEnabled = false
                            }
                            else -> genderSP.setSelection(0)
                        }
                        when (user.nationality) {
                            "Portuguese" -> {
                                nationalitySP.setSelection(1)
                                nationalitySP.isEnabled = false
                            }
                            "English" -> {
                                nationalitySP.setSelection(2)
                                nationalitySP.isEnabled = false
                            }
                            else -> nationalitySP.setSelection(0)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                print("error")
            }
        })
    }

    private fun getResidencesData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.UNIHOME_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val sp = GlobalFunctions.getSharedPreferencesContext(this@FormsActivity)
        val authToken = sp.getString("authToken", null)

        val service = retrofit.create(ResidenceService::class.java)
        val call = service.getAllResidences(authToken)

        call.enqueue(object : Callback<List<Residence>> {
            override fun onResponse(call: Call<List<Residence>>, response: Response<List<Residence>>) {
                if (response.code() == 200) {
                    val residences = response.body()

                    val spinnerItems = mutableListOf("-Choose Residence-")
                    val residenceNames = residences?.mapNotNull { it.name } ?: emptyList()
                    spinnerItems.addAll(residenceNames)

                    residencesSP.adapter = ArrayAdapter(
                        applicationContext,
                        android.R.layout.simple_spinner_item,
                        spinnerItems
                    )
                }
            }
            override fun onFailure(call: Call<List<Residence>>, t: Throwable) {
                print("error")
            }
        })
    }

    private fun updateUserData(userID: Int) {
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
            if (genderSP.selectedItemPosition != 0) genderSP.selectedItem.toString() else null,
            if (nationalitySP.selectedItemPosition != 0) nationalitySP.selectedItem.toString() else null,
            nifET.text.toString(),
            phoneNumberET.text.toString().toInt(),
            null,
            null,
            null,
            null
        )
        val sp = GlobalFunctions.getSharedPreferencesContext(this@FormsActivity)
        val authToken = sp.getString("authToken", null)

        val service = retrofit.create(UserService::class.java)
        val call = service.updateUser(authToken, userID, userPatchBody)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.code() == 200) {
                    Toast.makeText(applicationContext, "User updated successful!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(applicationContext, "Something went wrong\nupdating the user.\nPlease try again!", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    private fun submitApplicationData(userID: Int, roomTypeID: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.UNIHOME_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val applicationData = Application(
            null,
            null,
            null,
            sosNumberET.text.toString().takeIf { it.isNotBlank() }?.toInt(),
            courseNameET.text.toString(),
            currentCourseYearSP.selectedItem.toString(),
            courseYearStartedSP.selectedItem.toString(),
            lastYearStatusCB.isChecked,
            socialBenefitsCB.isChecked,
            observationsET.text.toString(),
            userID,
            null,
            roomTypeID,
            null,
            residencesSP.selectedItemPosition,
            null
        )
        val sp = GlobalFunctions.getSharedPreferencesContext(this@FormsActivity)
        val authToken = sp.getString("authToken", null)

        val service = retrofit.create(ApplicationService::class.java)
        val call = service.addApplication(authToken, applicationData)

        call.enqueue(object : Callback<Application> {
            override fun onResponse(call: Call<Application>, response: Response<Application>) {
                if (response.code() == 200) {
                    Toast.makeText(applicationContext, "Application submitted\nwith success!", Toast.LENGTH_LONG).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<Application>, t: Throwable) {
                Toast.makeText(applicationContext, "Something went wrong.\nPlease try again!", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

}