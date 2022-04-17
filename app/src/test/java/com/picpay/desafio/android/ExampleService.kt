package com.picpay.desafio.android

import com.picpay.desafio.android.core.remote.api.ContactApi
import com.picpay.desafio.android.core.model.ContactResponse
import com.picpay.desafio.android.core.remote.service.MyService
import retrofit2.Response

class ExampleService(
    private val api: ContactApi
) {

    suspend fun example():List<ContactResponse> {
        val contacts = api.getContactList().body()

        return contacts ?: emptyList()
    }
}