package com.marina_romanovna.apartmentcatalog.presentation.apartment.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.marina_romanovna.apartmentcatalog.databinding.ItemApartmentBinding
import com.marina_romanovna.apartmentcatalog.domain.models.ApartmentItem
import com.marina_romanovna.apartmentcatalog.utils.ApartmentDiffUtilCallback

class ApartmentListAdapter(
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<ApartmentItem, ApartmentItemViewHolder>(ApartmentDiffUtilCallback()) {

    interface OnItemClickListener {
        fun onItemClick(item: ApartmentItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApartmentItemViewHolder {
        return ApartmentItemViewHolder(
            ItemApartmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: ApartmentItemViewHolder, position: Int) {
        val apartmentItem = getItem(position)
        holder.bind(apartmentItem)
    }
}