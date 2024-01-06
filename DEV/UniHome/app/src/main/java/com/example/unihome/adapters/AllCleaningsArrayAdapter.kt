package com.example.unihome.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.unihome.R
import com.example.unihome.models.Cleaning

class AllCleaningsArrayAdapter(context: Context, resource: Int, objects: MutableList<Cleaning>) :
    ArrayAdapter<Cleaning>(context, resource, objects) {
    private var mContext: Context
    private var mValues: MutableList<Cleaning>
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
            view.tag = AllCleaningsViewHolder(view)
        }

        val vh: AllCleaningsViewHolder = view.tag as AllCleaningsViewHolder
        val currentCleaning = getItem(position)

        val cleaningDate = currentCleaning?.date.toString().substring(0,16).replace("T", " ")
        val cleaningStatus = currentCleaning?.status

        vh.cleaningDateTV?.text = cleaningDate
        vh.cleaningStateTV?.text = cleaningStatus

        return view
    }

    fun addCleaning(newCleaning: Cleaning) {
        mValues.add(newCleaning)
    }
}

private class AllCleaningsViewHolder(view: View?) {
    val cleaningDateTV = view?.findViewById<TextView>(R.id.layoutallcleanings_date_tv)
    val cleaningStateTV = view?.findViewById<TextView>(R.id.layoutallcleanings_status_tv)
}