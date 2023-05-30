package com.example.myapplication.repositories

import com.example.myapplication.models.Address

interface AddressRepository {
    suspend fun fetchAddress(cep: String): Address
}