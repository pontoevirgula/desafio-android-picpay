package com.picpay.desafio.android.ui.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.ContactApplication
import com.picpay.desafio.android.R
import com.picpay.desafio.android.core.model.ContactResponse
import com.picpay.desafio.android.core.util.Resource
import com.picpay.desafio.android.repository.IContactRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class ContactViewModel(
    app: Application,
    private val contactRepository: IContactRepository
) : AndroidViewModel(app) {

    val contactLiveData: MutableLiveData<Resource<List<ContactResponse>>> = MutableLiveData()

    fun fetchAllContacts() {
        viewModelScope.launch {
            contactLiveData.postValue(Resource.Loading())
            try{
                if (hasInternet()){
                    val response = contactRepository.getContactsFromApi()
                    contactLiveData.postValue(handleResponse(response))
                }else{
                    contactLiveData.postValue(Resource.Error("${R.string.error_internet}"))
                }
            }catch(t: Throwable){
                when(t){
                    is IOException -> contactLiveData.postValue(Resource.Error("${R.string.error_internet}"))
                    else -> contactLiveData.postValue(Resource.Error("${R.string.error_system}"))
                }
            }

        }
    }

    private fun handleResponse(response: Response<List<ContactResponse>>): Resource<List<ContactResponse>> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            } ?: kotlin.run {
                contactLiveData.postValue(Resource.Error("${R.string.empty_list}"))
                return Resource.Error(response.message())
            }
        } else {
            contactLiveData.postValue(Resource.Error("${R.string.error_system}"))
            return Resource.Error(response.message())
        }
    }

    fun saveContact(contact: ContactResponse) = viewModelScope.launch {
        contactRepository.insert(contact)
    }

    fun getFavoriteContact() = contactRepository.getFavoriteContacts()

    fun deleteContact(contact: ContactResponse) = viewModelScope.launch {
        contactRepository.deleteContact(contact)
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