package com.example.myapplication.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Address
import com.example.myapplication.repositories.AddressRepository
import kotlinx.coroutines.launch

class AddressViewModel(private val addressRepository: AddressRepository) : ViewModel() {
    private val _address = MutableLiveData<State>()
    val address: LiveData<State> get() = _address

    fun fetchAddress(cep: String) {
        viewModelScope.launch {
            _address.value = State.Loading
            try {
                val resource: Address = addressRepository.fetchAddress(cep)
                _address.value = State.Success(resource)
            } catch (e: Exception) {
                _address.value = State.Error(e.message.toString())
            }
        }
    }

    sealed interface State {
        object Loading : State
        data class Success(val data: Address) : State
        data class Error(val message: String) : State
    }
}
