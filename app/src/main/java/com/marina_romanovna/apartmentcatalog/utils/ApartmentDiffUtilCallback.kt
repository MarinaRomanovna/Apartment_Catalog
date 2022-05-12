package com.marina_romanovna.apartmentcatalog.utils

import androidx.recyclerview.widget.DiffUtil
import com.marina_romanovna.apartmentcatalog.domain.models.ApartmentItem

class ApartmentDiffUtilCallback: DiffUtil.ItemCallback<ApartmentItem>() {

    override fun areItemsTheSame(oldItem: ApartmentItem, newItem: ApartmentItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ApartmentItem, newItem: ApartmentItem): Boolean {
        return oldItem == newItem
    }
}