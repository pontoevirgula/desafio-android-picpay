package com.picpay.desafio.android.core.remote.repository

import com.picpay.desafio.android.core.model.ContactResponse
import com.picpay.desafio.android.core.remote.service.MyService
import retrofit2.Response

class ContactRepositoryImpl(private val service: MyService) :
    IContactRepository {
    override suspend fun getContactsFromApi(): Response<List<ContactResponse>> {
        return service.getService().getContactList()
    }
}