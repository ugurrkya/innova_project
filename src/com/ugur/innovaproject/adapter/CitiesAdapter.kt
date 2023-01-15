package com.ugur.innovaproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ugur.innovaproject.R
import com.ugur.innovaproject.model.CityResponse
import kotlinx.android.synthetic.main.city_layout.view.*


class CitiesAdapter : RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder>() {


    inner class CitiesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<CityResponse>() {
        override fun areItemsTheSame(oldItem: CityResponse, newItem: CityResponse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CityResponse, newItem: CityResponse): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        return CitiesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.city_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    private var onCheckedChangeListener: ((CityResponse) -> Unit)? = null

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        val city = differ.currentList[position]
        holder.itemView.apply {

            cityRow.text = city.name





            cityRow.setOnCheckedChangeListener(null)
            cityRow.isChecked = city.status == 1
            cityRow.setOnCheckedChangeListener { _, isChecked ->
                onCheckedChangeListener?.let { it(city) }
            }

        }
    }



    fun setOnCheckBoxChangeListener(listener: (CityResponse) -> Unit) {
        onCheckedChangeListener = listener
    }

}