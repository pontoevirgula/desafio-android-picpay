package com.picpay.desafio.android.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.picpay.desafio.android.core.db.IContactRepositoryDB
import com.picpay.desafio.android.core.remote.repository.IContactRepository

class ViewModelFactory(
    private val app: Application,
    private val contactRepository: IContactRepository,
    private val contactRepositoryDB: IContactRepositoryDB
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContactViewModel(app,contactRepository,contactRepositoryDB) as T
    }
}