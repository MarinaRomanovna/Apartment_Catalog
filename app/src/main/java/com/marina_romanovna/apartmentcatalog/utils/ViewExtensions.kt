package com.marina_romanovna.apartmentcatalog.utils

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.marina_romanovna.apartmentcatalog.domain.models.ApartmentItem
import com.marina_romanovna.apartmentcatalog.presentation.apartment.list.adapter.ApartmentListAdapter
import java.util.*

fun showSnackbar(view: View, text: String) {
    Snackbar.make(view, text, Snackbar.LENGTH_LONG)
        .setBackgroundTint(Color.GRAY)
        .show()
}

fun showSnackbarWithAction(
    view: View,
    attentionText: String,
    actionText: String,
    action: (View) -> Unit
) {
    Snackbar.make(view, attentionText, Snackbar.LENGTH_LONG)
        .setAction(
            actionText
        ) { action.invoke(it) }
        .setBackgroundTint(Color.GRAY)
        .show()
}

fun getNumberFormat(number: Int): String {
    return String.format(Locale.CANADA_FRENCH, "%,d", number)
}

fun setupLeftSwipeListener(
    recyclerView: RecyclerView,
    adapter: ApartmentListAdapter,
    action: (ApartmentItem) -> Unit
) {
    val callback = object :
        ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val item = adapter.currentList[viewHolder.adapterPosition]
            action.invoke(item)
        }
    }
    val itemTouchHelper = ItemTouchHelper(callback)
    itemTouchHelper.attachToRecyclerView(recyclerView)
}

