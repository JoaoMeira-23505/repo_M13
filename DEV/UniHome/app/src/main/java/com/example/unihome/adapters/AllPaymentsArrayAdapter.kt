package com.example.unihome.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.unihome.R
import com.example.unihome.models.Payment

class AllPaymentsArrayAdapter (context: Context, resource: Int, objects: List<Payment>) :
    ArrayAdapter<Payment>(context, resource, objects) {
    private var mContext: Context
    private var mValues: List<Payment>
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
            view.tag = AllPaymentsViewHolder(view)
        }

        val vh: AllPaymentsViewHolder = view.tag as AllPaymentsViewHolder
        val currentPayment = getItem(position)

        val paymentTypeName = currentPayment?.paymentType?.name
        val paymentValue = String.format("%.2f", currentPayment?.value) + " €"
        val paymentsStatus = currentPayment?.status

        vh.paymentTypeNameTV?.text = paymentTypeName
        vh.paymentValueTV?.text = paymentValue
        vh.paymentStatusTV?.text = paymentsStatus

        return view
    }
}

private class AllPaymentsViewHolder(view: View?) {
    val paymentTypeNameTV = view?.findViewById<TextView>(R.id.layoutallpayments_payment_type_tv)
    val paymentValueTV = view?.findViewById<TextView>(R.id.layoutallpayments_value_tv)
    val paymentStatusTV = view?.findViewById<TextView>(R.id.layoutallpayments_status_tv)
}