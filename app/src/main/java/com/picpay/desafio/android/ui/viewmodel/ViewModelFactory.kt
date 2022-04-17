package com.picpay.desafio.android.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.picpay.desafio.android.repository.IContactRepository

class ViewModelFactory(
    private val app: Application,
    private val contactRepository: IContactRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContactViewModel(app,contactRepository) as T
    }
}