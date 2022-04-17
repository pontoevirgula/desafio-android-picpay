package com.picpay.desafio.android.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.picpay.desafio.android.core.model.ContactResponse
import com.picpay.desafio.android.databinding.AdapterItemContactBinding

class ContactAdapter: RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private lateinit var binding : AdapterItemContactBinding

    inner class ContactViewHolder(bindingAdapter: AdapterItemContactBinding) :
        RecyclerView.ViewHolder(bindingAdapter.root)

    private val differCallback = object : DiffUtil.ItemCallback<ContactResponse>() {

        override fun areItemsTheSame(oldItem: ContactResponse, newItem: ContactResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ContactResponse, newItem: ContactResponse): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        binding =
            AdapterItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(contact.img).into(binding.contactImage)
            binding.contactName.text = contact.username
            binding.contactUsername.text = contact.name

            setOnClickListener {
                onItemClickListener?.let {
                    it(contact)
                }
            }
        }
    }

    private var onItemClickListener: ((ContactResponse) -> Unit)? = null

    fun setOnItemClickListener(listener: (ContactResponse) -> Unit) {
        onItemClickListener = listener
    }


}