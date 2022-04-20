package com.picpay.desafio.android.core.remote.repository

import com.picpay.desafio.android.core.model.ContactResponse
import retrofit2.Response

interface IContactRepository {
    suspend fun getContactsFromApi(): Response<List<ContactResponse>>
}