package com.picpay.desafio.android.core.di

import android.content.Context
import com.picpay.desafio.android.core.db.ContactDB
import com.picpay.desafio.android.core.remote.service.MyService
import com.picpay.desafio.android.core.remote.repository.ContactRepositoryImpl
import com.picpay.desafio.android.core.remote.repository.IContactRepository

object DependencyInjector {

    fun providerRepository(): IContactRepository = ContactRepositoryImpl(providerDB(),providerService())

    private fun providerService() = MyService

    private fun providerDB() = ContactDB.invoke(getContext())

    private fun getContext():Context{
        //TODO
        return getContext()
    }

}