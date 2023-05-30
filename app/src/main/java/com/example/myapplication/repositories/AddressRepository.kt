package com.example.myapplication.repositories

import com.example.myapplication.models.Address
import com.example.myapplication.models.State

interface AddressRepository {
    suspend fun fetchAddress(cep: String): State<Address>
}