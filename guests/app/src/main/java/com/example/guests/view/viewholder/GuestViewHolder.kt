package com.example.guests.view.viewholder

import android.app.AlertDialog
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.guests.R
import com.example.guests.service.model.GuestModel
import com.example.guests.view.listener.GuestListener
import kotlinx.android.synthetic.main.row_guest.view.*

class GuestViewHolder(itemView: View, private val listener: GuestListener) : RecyclerView.ViewHolder(itemView) {
    fun bind(guest: GuestModel){
        val textName = itemView.findViewById<TextView>(R.id.text_name)
        val buttonEditText = itemView.findViewById<ImageView>(R.id.button_edit_guest)
        val buttonRemoveText = itemView.findViewById<ImageView>(R.id.button_remove_guest)
        textName.text = guest.name

        buttonEditText.setOnClickListener {
            listener.onEdit(guest.id)
        }

        buttonRemoveText.setOnClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.guest_removal)
                .setMessage(R.string.want_to_remove)
                .setPositiveButton(R.string.remove){ dialog, which ->
                    listener.onDelete(guest.id)
                }
                .setNeutralButton(R.string.cancel, null)
                .show()
        }
    }
}