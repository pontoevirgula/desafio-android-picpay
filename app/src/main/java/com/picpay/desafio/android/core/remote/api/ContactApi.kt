package com.picpay.desafio.android.core.remote.api

import com.picpay.desafio.android.core.model.ContactResponse
import retrofit2.Response
import retrofit2.http.GET


interface ContactApi {

    @GET("users")
    suspend fun getContactList(): Response<List<ContactResponse>>
}