package com.picpay.desafio.android.core.db

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.core.model.ContactResponse

interface IContactRepositoryDB {

    suspend fun insert(contact: ContactResponse)

    suspend fun deleteContact(contact: ContactResponse)

    fun getFavoriteContacts() : LiveData<List<ContactResponse>>
}