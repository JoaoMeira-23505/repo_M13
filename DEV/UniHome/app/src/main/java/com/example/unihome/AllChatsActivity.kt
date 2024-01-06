package com.example.unihome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import com.example.unihome.adapters.AllChatsArrayAdapter
import com.example.unihome.utils.GlobalVariables.UNIHOME_URL
import com.example.unihome.models.Chat
import com.example.unihome.services.ChatService
import com.example.unihome.utils.GlobalFunctions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AllChatsActivity : AppCompatActivity() {
    private val allChatsLV: ListView by lazy { findViewById(R.id.allchats_chats_lv) }
    private val chatExitIV: ImageView by lazy { findViewById(R.id.allchats_exit_arrow_iv) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_chats)

        if(GlobalFunctions.isNetworkAvailable(applicationContext)){
            val userID: String? = intent.getStringExtra("userID")
            if (userID != null) {
                getChatsData(userID.toInt())
            }
            allChatsLV.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
                val chatId = (adapterView.adapter as AllChatsArrayAdapter).getItem(position)?.ID
                val chatTypeName = (adapterView.adapter as AllChatsArrayAdapter).getItem(position)?.chatType?.name
                val intent = Intent(this@AllChatsActivity, ChatActivity::class.java)

                intent.putExtra("ID", chatId.toString())
                intent.putExtra("chatTypeName", chatTypeName.toString())
                intent.putExtra("UserID", userID.toString())
                startActivity(intent)
            }
        }
        else{
            Toast.makeText(this, "No Internet!", Toast.LENGTH_LONG).show()
        }

        chatExitIV.setOnClickListener{
            finish()
        }
    }

    private fun getChatsData(userID: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(UNIHOME_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val sp = GlobalFunctions.getSharedPreferencesContext(this@AllChatsActivity)
        val authToken = sp.getString("authToken", null)

        val service = retrofit.create(ChatService::class.java)
        val call = service.getChatsByUserId(authToken, userID)

        call.enqueue(object : Callback<List<Chat>> {
            override fun onResponse(call: Call<List<Chat>>, response: Response<List<Chat>>) {
                if (response.code() == 200){
                    val chats = response.body()
                    chats?.let {
                        val adapter = AllChatsArrayAdapter(this@AllChatsActivity, R.layout.layout_all_chats_lv, it)
                        allChatsLV.adapter = adapter
                    }
                }
            }
            override fun onFailure(call: Call<List<Chat>>, t: Throwable) {
                print("error")
            }
        })
    }
}