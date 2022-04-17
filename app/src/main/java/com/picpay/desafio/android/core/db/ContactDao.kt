package com.picpay.desafio.android.core.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.picpay.desafio.android.core.model.ContactResponse

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: ContactResponse): Long

    @Query("SELECT * FROM contact")
    fun getAllContacts(): LiveData<List<ContactResponse>>

    @Delete
    suspend fun deleteContact(contact: ContactResponse)
}