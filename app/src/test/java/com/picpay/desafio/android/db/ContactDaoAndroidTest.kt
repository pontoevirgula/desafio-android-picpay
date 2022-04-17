package com.picpay.desafio.android.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.test.runner.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.picpay.desafio.android.core.db.ContactDB
import com.picpay.desafio.android.core.db.ContactDao
import com.picpay.desafio.android.core.model.ContactResponse
import com.picpay.desafio.android.livedata.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ContactDaoAndroidTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ContactDB
    private lateinit var dao: ContactDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ContactDB::class.java
        ).allowMainThreadQueries().build()
        dao = database.getContactDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertContactItem() = runBlockingTest {
        dao.insert(CONTACT_ITEM)

        val allContactItems = dao.getAllContacts().getOrAwaitValue()

        assertThat(allContactItems).contains(CONTACT_ITEM)
    }

    @Test
    fun deleteContactItem() = runBlockingTest {
        dao.insert(CONTACT_ITEM)
        dao.deleteContact(CONTACT_ITEM)

        val allContactItems = dao.getAllContacts().getOrAwaitValue()

        assertThat(allContactItems).doesNotContain(CONTACT_ITEM)
    }

    companion object {
        private val CONTACT_ITEM = ContactResponse(
            id = "1",
            img = "https://randomuser.me/api/portraits/men/1.jpg",
            name = "Sandrine Spinka",
            username = "Tod86"
        )
    }
}