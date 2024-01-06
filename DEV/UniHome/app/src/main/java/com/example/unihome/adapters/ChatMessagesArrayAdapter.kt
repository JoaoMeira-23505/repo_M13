package com.example.unihome.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.unihome.R
import com.example.unihome.models.Message

class ChatMessagesArrayAdapter(context: Context, resource: Int, objects: MutableList<Message>, user: Int) :
    ArrayAdapter<Message>(context, resource, objects) {
    private var mContext: Context
    private var mResource: Int
    private var mValues: MutableList<Message>
    private var mUser: Int

    init {
        mContext = context
        mResource = resource
        mValues = objects
        mUser = user
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View

        if (convertView != null) {
            view = convertView
        }
        else{
            view = LayoutInflater.from(context).inflate(mResource, parent, false)
            view.tag = ChatMessagesViewHolder(view)
        }

        val vh: ChatMessagesViewHolder = view.tag as ChatMessagesViewHolder
        val currentMessage = getItem(position)

        val messageText = currentMessage?.text
        val messageDate = currentMessage?.dateSended.toString().substring(11,16)
        val layoutParams = vh.messageLL?.layoutParams as ConstraintLayout.LayoutParams

        if (currentMessage?.UserID == mUser){
            if (layoutParams.startToStart == ConstraintLayout.LayoutParams.PARENT_ID) {
                layoutParams.startToStart = ConstraintLayout.LayoutParams.UNSET
                layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID

                vh.messageLL.layoutParams = layoutParams
            }

            vh.messageLL.setBackgroundResource(R.drawable.message_bubble_sender)
            vh.messageTextTV?.setTextColor(ContextCompat.getColor(context, R.color.white))
            vh.messageDateTV?.setTextColor(ContextCompat.getColor(context, R.color.westar))
        }
        else {
            if (layoutParams.endToEnd == ConstraintLayout.LayoutParams.PARENT_ID) {
                layoutParams.endToEnd = ConstraintLayout.LayoutParams.UNSET
                layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID

                vh.messageLL.layoutParams = layoutParams
            }

            vh.messageLL.setBackgroundResource(R.drawable.message_bubble_receiver)
            vh.messageTextTV?.setTextColor(ContextCompat.getColor(context, R.color.lightBrown))
            vh.messageDateTV?.setTextColor(ContextCompat.getColor(context, R.color.keppel))
        }

        vh.messageTextTV?.text = messageText
        vh.messageDateTV?.text = messageDate

        return view
    }

    fun addMessage(newMessage: Message) {
        mValues.add(newMessage)
    }
}

private class ChatMessagesViewHolder(view: View?) {
    val messageLL = view?.findViewById<LinearLayout>(R.id.layoutchatmsg_message_ll)
    val messageTextTV = view?.findViewById<TextView>(R.id.layoutchatmsg_text_tv)
    val messageDateTV = view?.findViewById<TextView>(R.id.layoutchatmsg_dateSended_tv)
}