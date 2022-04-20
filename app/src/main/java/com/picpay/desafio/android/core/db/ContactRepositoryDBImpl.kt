package com.picpay.desafio.android.core.db

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.core.model.ContactResponse

class ContactRepositoryDBImpl(private val db: ContactDB) : IContactRepositoryDB  {
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