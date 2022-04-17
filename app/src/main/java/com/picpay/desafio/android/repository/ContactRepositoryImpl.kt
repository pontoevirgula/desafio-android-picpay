package com.picpay.desafio.android.repository

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.core.db.ContactDB
import com.picpay.desafio.android.core.model.ContactResponse
import com.picpay.desafio.android.core.remote.service.MyService
import retrofit2.Response

class ContactRepositoryImpl(private val db: ContactDB, private val service: MyService) :
    IContactRepository {
    override suspend fun getContactsFromApi(): Response<List<ContactResponse>> {
        return service.getService().getContactList()
    }

    override suspend fun insert(contact: ContactResponse) {
        db.getContactDao().insert(contact)
    }

    override suspend fun deleteContact(contact: ContactResponse) {
        db.getContactDao().deleteContact(contact)
    }

    override fun getFavoriteContacts(): LiveData<List<ContactResponse>> {
        return db.getContactDao().getAllContacts()
    }
}