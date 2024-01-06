package com.example.unihome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.room.Room
import com.example.unihome.entities.RoomTypeEntity
import com.example.unihome.models.RoomType
import com.example.unihome.services.RoomTypeService
import com.example.unihome.utils.GlobalFunctions
import com.example.unihome.utils.GlobalVariables
import com.example.unihome.utils.InternalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChooseRoomTypeActivity : AppCompatActivity() {
    private val chooseRoomExitIV: ImageView by lazy { findViewById(R.id.room_exit_arrow_iv) }
    private val optionSimpleSingleRoomBT: Button by lazy { findViewById(R.id.chooseroom_type_ss_bt) }
    private val optionSimpleDuplexRoomBT: Button by lazy { findViewById(R.id.chooseroom_type_sd_bt) }
    private val optionPremiumSingleRoomBT: Button by lazy { findViewById(R.id.chooseroom_type_ps_bt) }
    private val optionPremiumDuplexRoomBT: Button by lazy { findViewById(R.id.chooseroom_type_pd_bt) }

    private val db: InternalDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            InternalDatabase::class.java,
            "roomType.db"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_room_type)

        if (GlobalFunctions.isNetworkAvailable(applicationContext))
            getRoomTypesData()
        else
            getInternalRoomTypesData()

        optionSimpleSingleRoomBT.setOnClickListener {
            val intent = Intent(this@ChooseRoomTypeActivity, RoomActivity::class.java)
            intent.putExtra("roomTypeID", optionSimpleSingleRoomBT.tag.toString())
            startActivity(intent)
        }
        optionSimpleDuplexRoomBT.setOnClickListener {
            val intent = Intent(this@ChooseRoomTypeActivity, RoomActivity::class.java)
            intent.putExtra("roomTypeID", optionSimpleDuplexRoomBT.tag.toString())
            startActivity(intent)
        }
        optionPremiumSingleRoomBT.setOnClickListener {
            val intent = Intent(this@ChooseRoomTypeActivity, RoomActivity::class.java)
            intent.putExtra("roomTypeID", optionPremiumSingleRoomBT.tag.toString())
            startActivity(intent)
        }
        optionPremiumDuplexRoomBT.setOnClickListener {
            val intent = Intent(this@ChooseRoomTypeActivity, RoomActivity::class.java)
            intent.putExtra("roomTypeID", optionPremiumDuplexRoomBT.tag.toString())
            startActivity(intent)
        }
        chooseRoomExitIV.setOnClickListener {
            finish()
        }
    }

    private fun getRoomTypesData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.UNIHOME_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RoomTypeService::class.java)
        val call = service.getAllRoomTypes()

        call.enqueue(object : Callback<List<RoomType>> {
            override fun onResponse(call: Call<List<RoomType>>, response: Response<List<RoomType>>) {
                if (response.code() == 200){
                    val roomType = response.body()
                    if (roomType != null) {
                        runOnUiThread {
                            GlobalScope.launch(Dispatchers.IO) {
                                for (rt in roomType) {
                                    db.roomTypeDao().insertAll(
                                        RoomTypeEntity(
                                            ID = rt.ID,
                                            name = rt.name,
                                            price = rt.price,
                                            description = rt.description
                                        )
                                    )
                                }
                                getInternalRoomTypesData()
                            }
                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<RoomType>>, t: Throwable) {
                print("error")
            }
        })
    }

    private fun getInternalRoomTypesData() {
        GlobalScope.launch(Dispatchers.IO) {
            val data = db.roomTypeDao().getAll()
            fillData(data)
        }
    }

    private fun fillData(roomTypeData: List<RoomTypeEntity>){
        runOnUiThread {
            for (rt in roomTypeData){
                when (rt.name) {
                    "Simple single room" -> {
                        optionSimpleSingleRoomBT.text = rt.name
                        optionSimpleSingleRoomBT.tag = rt.ID
                    }
                    "Simple duplex room" -> {
                        optionSimpleDuplexRoomBT.text = rt.name
                        optionSimpleDuplexRoomBT.tag = rt.ID
                    }
                    "Premium single room" -> {
                        optionPremiumSingleRoomBT.text = rt.name
                        optionPremiumSingleRoomBT.tag = rt.ID
                    }
                    "Premium duplex room" -> {
                        optionPremiumDuplexRoomBT.text = rt.name
                        optionPremiumDuplexRoomBT.tag = rt.ID
                    }
                }
            }
        }
    }
}