package com.marina_romanovna.apartmentcatalog.presentation.apartment.list.adapter

import androidx.recyclerview.widget.RecyclerView
import com.marina_romanovna.apartmentcatalog.databinding.ItemApartmentBinding
import com.marina_romanovna.apartmentcatalog.domain.models.ApartmentItem
import com.marina_romanovna.apartmentcatalog.utils.getNumberFormat

class ApartmentItemViewHolder(
    private val binding: ItemApartmentBinding,
    private val listener: ApartmentListAdapter.OnItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ApartmentItem) {
        itemView.setOnClickListener {
            listener.onItemClick(item)
        }
        binding.apply {
            tvPrice.text = getNumberFormat(item.price)
            tvSquare.text = item.square.toString()
            tvAddress.text = item.address
        }
    }
}