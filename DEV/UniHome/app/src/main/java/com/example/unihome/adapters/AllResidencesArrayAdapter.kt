package com.example.unihome.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.unihome.R
import com.example.unihome.models.Residence

class AllResidencesArrayAdapter(context: Context, resource: Int, objects: MutableList<Residence>) :
    ArrayAdapter<Residence>(context, resource, objects) {
    private var mContext: Context
    private var mValues: MutableList<Residence>
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
            view.tag = AllResidencesViewHolder(view)
        }

        val vh: AllResidencesViewHolder = view.tag as AllResidencesViewHolder
        val currentResidence = getItem(position)

        val residenceName = currentResidence?.name
        val residenceLocation = currentResidence?.location

        vh.residenceNameTV?.text = residenceName
        vh.residenceLocationTV?.text = residenceLocation

        return view
    }

    fun addCleaning(newResidence: Residence) {
        mValues.add(newResidence)
    }
}

private class AllResidencesViewHolder(view: View?) {
    val residenceNameTV = view?.findViewById<TextView>(R.id.layoutallresidences_name_tv)
    val residenceLocationTV = view?.findViewById<TextView>(R.id.layoutallresidences_location_tv)
}