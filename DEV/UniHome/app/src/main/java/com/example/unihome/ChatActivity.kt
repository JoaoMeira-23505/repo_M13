package com.example.unihome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.unihome.adapters.ChatMessagesArrayAdapter
import com.example.unihome.utils.GlobalVariables.UNIHOME_URL
import com.example.unihome.models.Message
import com.example.unihome.services.MessageService
import com.example.unihome.utils.GlobalFunctions
import com.example.unihome.utils.GlobalVariables.UNIHOME_WEB_SOCKET
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

class ChatActivity : AppCompatActivity() {
    private lateinit var chatWebSocket: WebSocket
    private val chatNameTV: TextView by lazy { findViewById(R.id.chat_chat_name_tv) }
    private val chatExitTV: TextView by lazy { findViewById(R.id.chat_x_tv) }
    private val chatMessagesLV: ListView by lazy { findViewById(R.id.chat_messages_lv) }
    private val chatWriteMessageET: EditText by lazy { findViewById(R.id.chat_write_message_et) }
    private val chatSendMessageBT: Button by lazy { findViewById(R.id.chat_send_message_bt) }
    private val chatBottomLineV: View by lazy { findViewById(R.id.line_separator_bottom_v) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val dataID: String = intent.getStringExtra("ID").toString()
        val dataChatTypeName: String = intent.getStringExtra("chatTypeName").toString()
        val userID: String = intent.getStringExtra("UserID").toString()

        if (dataChatTypeName != "Admin Chat"){
            chatWriteMessageET.visibility = INVISIBLE
            chatSendMessageBT.visibility = INVISIBLE
            chatBottomLineV.visibility = INVISIBLE

            val layoutParams = chatMessagesLV.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.bottomToTop = ConstraintLayout.LayoutParams.UNSET
            layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            chatMessagesLV.layoutParams = layoutParams
        }

        chatNameTV.text = dataChatTypeName

        getMessagesData(dataID.toInt(), userID.toInt())
        chatExitTV.setOnClickListener{
            finish()
            chatWebSocket.cancel()
        }
        // Connect to WebSocket
        connectWebSocket(dataID.toInt(), userID.toInt())
    }

    override fun onDestroy() {
        super.onDestroy()
        chatWebSocket.cancel()
    }

    private fun getMessagesData(chatID: Int, userID: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(UNIHOME_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val sp = GlobalFunctions.getSharedPreferencesContext(this@ChatActivity)
        val authToken = sp.getString("authToken", null)

        val service = retrofit.create(MessageService::class.java)
        val call = service.getMessagesByChatId(authToken, chatID)

        call.enqueue(object : Callback<List<Message>> {
            override fun onResponse(call: Call<List<Message>>, response: Response<List<Message>>) {
                if (response.code() == 200){
                    val messages = response.body()
                    messages?.let {
                        val mutableMessages = it.toMutableList() // Convert to a MutableList<Message>
                        val adapter = ChatMessagesArrayAdapter(this@ChatActivity, R.layout.layout_chat_messages_lv, mutableMessages, userID)
                        chatMessagesLV.adapter = adapter
                        chatMessagesLV.setSelection(adapter.count - 1)
                    }
                }
            }
            override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                print("error")
            }
        })
    }

    private fun connectWebSocket(chatID: Int, userID: Int) {
        val request = Request.Builder()
            .url(UNIHOME_WEB_SOCKET + chatID)
            .build()

        val client = OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .build()

        chatWebSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                super.onOpen(webSocket, response)
                // WebSocket connected
                // You can send messages here if needed after connection

                chatSendMessageBT.setOnClickListener{
                    if (chatExitTV.text.isNotBlank()) {
                        val newMessage =
                            "{ \"text\":\"${chatWriteMessageET.text}\"," +
                                    " \"ChatID\":$chatID," +
                                    " \"UserID\":$userID }"
                        sendMessage(newMessage)
                        chatWriteMessageET.text.clear()
                    } else {
                        Toast.makeText(applicationContext, "Please enter a message", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)

                val message = convertStringToMessage(text)

                val adapter = chatMessagesLV.adapter as? ChatMessagesArrayAdapter
                adapter?.addMessage(message)
                chatMessagesLV.postDelayed({
                    adapter?.notifyDataSetChanged() // Notify the adapter of changes
                    chatMessagesLV.smoothScrollToPosition(adapter?.count ?: 0)
                }, 50)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
                super.onFailure(webSocket, t, response)
                // Handle connection failure
            }
        })
    }

    private fun sendMessage(message: String) {
        chatWebSocket.send(message)
    }

    private fun convertStringToMessage(messageString: String): Message {
        val jsonObject = JSONObject(messageString)

        val id = jsonObject.getInt("ID")
        val text = jsonObject.getString("text")

        val dateSendedString = jsonObject.getString("dateSended")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()) // Adjust the date format to match your incoming date string format
        val parsedDate = dateFormat.parse(dateSendedString)
        val timestamp = Timestamp(parsedDate.time)

        val chatID = jsonObject.getInt("ChatID")
        val userID: Int? = if (jsonObject.isNull("UserID")) {
            null
        } else {
            jsonObject.getInt("UserID")
        }

        return Message(id, text, timestamp, chatID, null, userID, null )
    }
}