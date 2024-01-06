package com.example.unihome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import com.example.unihome.entities.RoomTypeEntity
import com.example.unihome.utils.GlobalFunctions
import com.example.unihome.utils.InternalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RoomActivity : AppCompatActivity() {
    private val roomApplyBT: Button by lazy { findViewById(R.id.room_apply_bt) }
    private val roomExitBT: ImageView by lazy { findViewById(R.id.room_exit_arrow_iv) }
    private val roomTitleTV: TextView by lazy { findViewById(R.id.room_title_tv) }
    private val roomPriceTV: TextView by lazy { findViewById(R.id.room_price_tv) }
    private val roomDescriptionTV: TextView by lazy { findViewById(R.id.room_description_tv) }

    private val db: InternalDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            InternalDatabase::class.java,
            "roomType.db"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        val roomTypeID: String? = intent.getStringExtra("roomTypeID")
        if (roomTypeID != null){
            getInternalRoomTypesData(roomTypeID.toInt())

            roomApplyBT.setOnClickListener {
                if (GlobalFunctions.isNetworkAvailable(applicationContext)) {
                    val intent = Intent(this@RoomActivity, FormsActivity::class.java)
                    intent.putExtra("roomTypeID", roomTypeID)
                    intent.putExtra("roomTypeName", roomTitleTV.text.toString())
                    startActivity(intent)
                }
                else
                    Toast.makeText(this, "No Internet!", Toast.LENGTH_LONG).show()
            }
        }

        roomExitBT.setOnClickListener {
            finish()
        }
    }

    private fun getInternalRoomTypesData(roomTypeID: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            val data = db.roomTypeDao().findById(roomTypeID)
            fillData(data)
        }
    }

    private fun fillData(roomTypeData: RoomTypeEntity){
        val roomPrice = String.format("%.2f", roomTypeData.price) + " €"
        roomTitleTV.text = roomTypeData.name
        roomPriceTV.text = roomPrice
        roomDescriptionTV.text = roomTypeData.description
    }
}