package com.example.myapplication.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Address
import com.example.myapplication.models.State
import com.example.myapplication.repositories.CepRepository
import kotlinx.coroutines.launch

class CepViewModel(private val cepRepository: CepRepository) : ViewModel() {
    private val _address = MutableLiveData<State<Address>>()
    val address: LiveData<State<Address>> get() = _address

    fun fetchCepDetails(cep: String) {
        viewModelScope.launch {
            _address.value = State.Loading
            try {
                val resource: State<Address> = cepRepository.fetchCepDetails(cep)
                _address.value = resource
            } catch (e: Exception) {
                _address.value = State.Error(e.message.toString())
            }
        }
    }
}
