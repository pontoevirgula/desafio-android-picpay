package com.picpay.desafio.android.repository

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.core.model.ContactResponse
import retrofit2.Response

interface IContactRepository {

    suspend fun getContactsFromApi() : Response<List<ContactResponse>>

    suspend fun insert(contact: ContactResponse)

    suspend fun deleteContact(contact: ContactResponse)

    fun getFavoriteContacts() : LiveData<List<ContactResponse>>
}