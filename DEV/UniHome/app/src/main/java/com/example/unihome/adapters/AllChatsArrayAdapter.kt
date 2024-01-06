package com.example.unihome.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.unihome.R
import com.example.unihome.models.Chat

class AllChatsArrayAdapter(context: Context, resource: Int, objects: List<Chat>) :
    ArrayAdapter<Chat>(context, resource, objects) {
    private var mContext: Context
    private var mValues: List<Chat>
    private var mResource: Int

    init {
        mContext = context
        mValues = objects
        mResource = resource
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View

        if (convertView != null) {
            view = convertView
        }
        else{
            view = LayoutInflater.from(context).inflate(mResource, parent, false)
            view.tag = AllChatsViewHolder(view)
        }

        val vh: AllChatsViewHolder = view.tag as AllChatsViewHolder
        val currentChat = getItem(position)

        val chatTypeName = "ChatType: ${currentChat?.chatType?.name}"
        val chatStatus = currentChat?.status

        vh.chatTypeNameTV?.text = chatTypeName
        vh.chatStatusTV?.text = chatStatus

        return view
    }
}

private class AllChatsViewHolder(view: View?) {
    val chatTypeNameTV = view?.findViewById<TextView>(R.id.layoutallchats_chat_type_name_tv)
    val chatStatusTV = view?.findViewById<TextView>(R.id.layoutallchats_chat_status_tv)
}