package com.picpay.desafio.android

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.core.remote.api.ContactApi
import com.picpay.desafio.android.core.model.ContactResponse
import junit.framework.Assert.assertEquals
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class ExampleServiceTest {

    private val api = mock<ContactApi>()

    private val service = ExampleService(api)

    @Test
    suspend fun exampleTest() {
        val call = mock<Response<List<ContactResponse>>>()
        val expectedUsers = emptyList<ContactResponse>()

        whenever(call).thenReturn(Response.success(expectedUsers))
        whenever(api.getContactList()).thenReturn(call)

        // when
        val contacts = service.example()

        // then
        assertEquals(contacts, expectedUsers)
    }
}