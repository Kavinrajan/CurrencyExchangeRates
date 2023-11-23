package com.task.currexchangerates.view.all_currency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.task.currexchangerates.R


class ConversionListAdapter(val conversionMap : Map<String, Double>)
    : RecyclerView.Adapter<ConversionListAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val code: TextView
        val rate: TextView

        init {
            view.apply {
                code = findViewById(R.id.currency)
                rate = findViewById(R.id.currency_detail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.currency_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val key = conversionMap.keys.elementAt(position)
        holder.code.text = key
        holder.rate.text = conversionMap.get(key).toString()
    }

    override fun getItemCount(): Int = conversionMap.size
}

data class Currency(val code: String, val rate: Double)
