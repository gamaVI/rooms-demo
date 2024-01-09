package com.example.businessscout.ui.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.businessscout.data.Bussiness
import com.example.businessscout.databinding.BusinessItemBinding

class BusinessAdapter : RecyclerView.Adapter<BusinessAdapter.BusinessViewHolder>() {

    private var businesses: List<Bussiness> = listOf()

    fun setBusinesses(newBusinesses: List<Bussiness>) {
        businesses = newBusinesses
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessViewHolder {
        val binding = BusinessItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BusinessViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BusinessViewHolder, position: Int) {
        val business = businesses[position]
        holder.bind(business)
    }

    override fun getItemCount(): Int = businesses.size

    class BusinessViewHolder(private val binding: BusinessItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(business: Bussiness) {
            binding.tvBusinessName.text = business.naziv
            binding.tvBusinessDetails.text = business.address
            // Add more fields as needed
        }
    }
}
