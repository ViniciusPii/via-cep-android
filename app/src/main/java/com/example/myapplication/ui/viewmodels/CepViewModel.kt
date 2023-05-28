package com.example.myapplication.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Cep
import com.example.myapplication.repositories.CepRepositoryImpl
import kotlinx.coroutines.launch

class CepViewModel(private val cepRepository: CepRepositoryImpl) : ViewModel() {
    private val _cepDetails = MutableLiveData<Resource<Cep>>()
    val cepDetails: LiveData<Resource<Cep>> get() = _cepDetails

    fun fetchCepDetails(cep: String) {
        viewModelScope.launch {
            _cepDetails.value = Resource.Loading
            val resource = cepRepository.fetchCepDetails(cep)
            _cepDetails.value = resource
        }
    }

    sealed class Resource<out T> {
        object Loading : Resource<Nothing>()
        data class Success<out T>(val data: T) : Resource<T>()
        data class Error(val message: String) : Resource<Nothing>()
    }
}
