package com.picpay.desafio.android

import com.picpay.desafio.android.core.remote.api.ContactApi
import com.picpay.desafio.android.core.model.ContactResponse

class ExampleService(
    private val service: ContactApi
) {

    fun example(): List<ContactResponse> {
        val users = service.getUsers().execute()

        return users.body() ?: emptyList()
    }
}