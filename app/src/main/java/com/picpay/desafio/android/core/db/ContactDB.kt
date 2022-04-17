package com.picpay.desafio.android.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.picpay.desafio.android.core.model.ContactResponse

@Database(
    entities = [ContactResponse::class],
    version = 1
)

abstract class ContactDB : RoomDatabase() {

    abstract fun getContactDao() : ContactDao

    companion object{
        @Volatile
        private var instance : ContactDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ContactDB::class.java,
            "contact_db.db"
        ).build()
    }
}