package com.picpay.desafio.android.ui.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.ContactApplication
import com.picpay.desafio.android.R
import com.picpay.desafio.android.core.db.IContactRepositoryDB
import com.picpay.desafio.android.core.model.ContactResponse
import com.picpay.desafio.android.core.util.Resource
import com.picpay.desafio.android.core.remote.repository.IContactRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class ContactViewModel(
    app: Application,
    private val contactRepository: IContactRepository,
    private val contactRepositoryDB: IContactRepositoryDB
) : AndroidViewModel(app) {

    private val _contactLiveData: MutableLiveData<Resource<List<ContactResponse>>> = MutableLiveData()
    val contactLiveData : LiveData<Resource<List<ContactResponse>>> = _contactLiveData

    fun fetchAllContacts() {
        viewModelScope.launch {
            _contactLiveData.postValue(Resource.Loading())
            try{
                if (hasInternet()){
                    val response = contactRepository.getContactsFromApi()
                    _contactLiveData.postValue(handleResponse(response))
                }else{
                    _contactLiveData.postValue(Resource.Error("${R.string.error_internet}"))
                }
            }catch(t: Throwable){
                when(t){
                    is IOException -> _contactLiveData.postValue(Resource.Error("${R.string.error_internet}"))
                    else -> _contactLiveData.postValue(Resource.Error("${R.string.error_system}"))
                }
            }

        }
    }

    private fun handleResponse(response: Response<List<ContactResponse>>): Resource<List<ContactResponse>> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            } ?: kotlin.run {
                _contactLiveData.postValue(Resource.Error("${R.string.empty_list}"))
                return Resource.Error(response.message())
            }
        } else {
            _contactLiveData.postValue(Resource.Error("${R.string.error_system}"))
            return Resource.Error(response.message())
        }
    }

    fun saveContact(contact: ContactResponse) = viewModelScope.launch {
        contactRepositoryDB.insert(contact)
    }

    fun getFavoriteContact() = contactRepositoryDB.getFavoriteContacts()

    fun deleteContact(contact: ContactResponse) = viewModelScope.launch {
        contactRepositoryDB.deleteContact(contact)
    }

    @Suppress("DEPRECATION")
    private fun hasInternet() : Boolean {
        val connectivityManager = getApplication<ContactApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

}