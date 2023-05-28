package com.example.myapplication.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Cep
import com.example.myapplication.models.Resource
import com.example.myapplication.repositories.CepRepository
import kotlinx.coroutines.launch

class CepViewModel(private val cepRepository: CepRepository) : ViewModel() {
    private val _cepDetails = MutableLiveData<Resource<Cep>>()
    val cepDetails: LiveData<Resource<Cep>> get() = _cepDetails

    fun fetchCepDetails(cep: String) {
        viewModelScope.launch {
            _cepDetails.value = Resource.Loading
            try {
                val resource = cepRepository.fetchCepDetails(cep)
                _cepDetails.value = resource
            } catch (e: Exception) {
                _cepDetails.value = Resource.Error(e.message.toString())
            }
        }
    }
}
