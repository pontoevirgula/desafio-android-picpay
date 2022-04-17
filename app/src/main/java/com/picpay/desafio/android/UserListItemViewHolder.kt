package com.picpay.desafio.android

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.core.model.ContactResponse
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class UserListItemViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

//    fun bind(contactResponse: ContactResponse) {
//        itemView.name.text = contactResponse.name
//        itemView.username.text = contactResponse.username
//        itemView.progressBar.visibility = View.VISIBLE
//        Picasso.get()
//            .load(contactResponse.img)
//            .error(R.drawable.ic_round_account_circle)
//            .into(itemView.picture, object : Callback {
//                override fun onSuccess() {
//                    itemView.progressBar.visibility = View.GONE
//                }
//
//                override fun onError(e: Exception?) {
//                    itemView.progressBar.visibility = View.GONE
//                }
//            })
//    }
}