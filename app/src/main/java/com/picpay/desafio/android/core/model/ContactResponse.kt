package com.picpay.desafio.android.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "contact"
)
data class ContactResponse(
    @PrimaryKey val id: String,
    val img: String,
    val name: String,
    val username: String
) : Serializable